package eu.yeger.refunk.recursive

import eu.yeger.refunk.base.*
import eu.yeger.refunk.base.Function

infix fun Function.with(other: Function) = Pair(this, other)

fun recursion(block: () -> Pair<Function, Function>) = with(block.invoke()) { Recursion(first, second) }

fun recursionOf(baseCase: Function,
                recursiveCase: Function,
                collector: () -> Array<Function>) = Recursion(baseCase, recursiveCase).of(collector)

//(x,y) -> x + y
fun addition(): Function {
    return recursion { first() with (first() andThen s()) }
}

fun additionOf(collector: () -> Array<Function>) = addition().of(collector)

//x -> x + value
fun add(value: Long) = additionOf { first() and c(value) }

//x -> x - 1
fun predecessor() = recursion { zero() with second() }

//(x,y) -> x - y
fun subtraction(): Function {
    val first = first()

    return recursionOf(first, first andThen predecessor()) { second() and first }
}

fun subtractionOf(collector: () -> Array<Function>) = subtraction().of(collector)

//x -> x - value
fun subtract(value: Long) = subtractionOf { first() and c(value) }

//x -> value - x
fun subtractFrom(value: Long) = subtractionOf { c(value) and first() }

fun not() = subtractFrom(1)

//(x,y) -> x * y
fun multiplication() = recursion { zero() with additionOf { first() and third() } }

fun multiplicationOf(collector: () -> Array<Function>) = multiplication().of(collector)

//x -> x * value
fun multiplyBy(value: Long) = multiplicationOf { first() and c(value) }

//x -> x²
fun square() = multiplicationOf { first() and first() }

//(x,y) -> x^y
fun exp() = recursionOf(one(), multiplicationOf { first() and third() }) { second() and first() }

fun expOf(collector: () -> Array<Function>) = exp().of(collector)

fun caseDifferentiation(differentiationFunction: Function,
                        zeroCaseFunction: Function,
                        otherCaseFunction: Function): Function {
    val zeroCaseTestFunction = multiplicationOf {
        zeroCaseFunction and (differentiationFunction andThen not())
    }

    val otherCaseTestFunction = multiplicationOf {
        otherCaseFunction and (differentiationFunction andThen not() andThen not())
    }

    return additionOf { zeroCaseTestFunction and otherCaseTestFunction }
}

fun boundedMuOperator(function: Function) =
    recursion { zero() with
            caseDifferentiation(
                boundedMuOperatorDifferentiationFunction(function),
                second() andThen s(),
                first()
            ) }

fun boundedMuOperatorOf(function: Function, collector: () -> Array<Function>) = boundedMuOperator(function).of(collector)

internal fun boundedMuOperatorDifferentiationFunction(function: Function): Function {
    val firstTestArguments = Array<Function>(function.arity) { p(it + 1) }
    firstTestArguments[0] = second() andThen s()
    val firstTestFunction = function of { firstTestArguments }

    val secondTestFunction = first()

    val thirdTestArguments = firstTestArguments.clone()
    thirdTestArguments[0] = zero()
    val thirdTestFunction = function of { thirdTestArguments }

    return additionOf {
        firstTestFunction and additionOf {
            secondTestFunction and subtractionOf { one() and thirdTestFunction }
        }
    }
}

//(x,y) -> ceiling(x / y)
fun ceilingDivision(): Function {
    //(n,x,y) -> x - n * y
    val g = subtractionOf { second() and multiplicationOf { first() and third() } }

    return boundedMuOperatorOf(g) { first() and first() and second() }
}

fun ceilingDivisionOf(collector: () -> Array<Function>) = ceilingDivision().of(collector)

//(x,y) -> floor(x / y)
//or 0 if y == 0
fun floorDivision(): Function {
    val ceilingDivision = ceilingDivision()

    val differentiationFunction = subtractionOf {
        multiplicationOf { ceilingDivision and second() } and first()
    }

    return caseDifferentiation(
        differentiationFunction,
        ceilingDivision,
        ceilingDivision andThen predecessor()
    )
}

fun floorDivisionOf(collector: () -> Array<Function>) = floorDivision().of(collector)

//(x,y) -> x / y; if x / y is a natural number
//(x,y) -> 0; else
fun division(): Function {
    //(n,x,y) -> (x - n * y) + (n * y - x)
    val g = additionOf {
        subtractionOf {
            second() and multiplicationOf { first() and third() }
        } and subtractionOf {
            multiplication() of { first() and third() } and second()
        }
    }

    return boundedMuOperatorOf(g) { first() and first() and second() }
}

fun divisionOf(collector: () -> Array<Function>) = division().of(collector)

//WARNING Due to the nature of recursive functions using log will likely result in a StackOverflowError
//x -> logBase(x); if logBase(x) is a natural number
//x -> 0; else
fun log(base: Long): Function {
    val firstTestFunction = subtractionOf {
        second() and (expOf {
            c(base) and first()
        })
    }

    val secondTestFunction = subtractionOf {
        expOf { c(base) and first() } and second()
    }

    val testFunction = additionOf { firstTestFunction and secondTestFunction }

    return boundedMuOperatorOf(testFunction) { first() and first() }
}
