package eu.yeger.refunk.recursive

import eu.yeger.refunk.base.*
import eu.yeger.refunk.base.Function

//(x,y) -> x + y
fun addition(): Function = recursive { first() andThen s() } withBaseCase first()

inline fun additionOf(collector: () -> Array<Function>) = addition().of(collector)

//x -> x + value
fun add(value: Long) = additionOf { first() and c(value) }

//x -> x - 1
fun predecessor() = recursive(second()) withBaseCase zero()

//(x,y) -> x - y
fun subtraction(): Function =
    recursive { first() andThen predecessor() } withBaseCase first() of { second() and first() }

inline fun subtractionOf(collector: () -> Array<Function>) = subtraction().of(collector)

//x -> x - value
fun subtract(value: Long) = subtractionOf { first() and c(value) }

//x -> value - x
fun subtractFrom(value: Long) = subtractionOf { c(value) and first() }

fun not() = subtractFrom(1)

//(x,y) -> x * y
fun multiplication() = recursive(additionOf { first() and third() }) withBaseCase zero()

inline fun multiplicationOf(collector: () -> Array<Function>) = multiplication().of(collector)

//x -> x * value
fun multiplyBy(value: Long) = multiplicationOf { first() and c(value) }

//x -> x²
fun square() = multiplicationOf { first() and first() }

//(x,y) -> x^y
fun exp() = recursive(multiplicationOf { first() and third() }) withBaseCase one() of { second() and first() }

inline fun expOf(collector: () -> Array<Function>) = exp().of(collector)

fun caseDifferentiation(
    differentiator: Function,
    zeroCase: Function,
    otherCase: Function
): Function {
    val zeroCaseTestFunction = multiplicationOf {
        zeroCase and (differentiator andThen not())
    }

    val otherCaseTestFunction = multiplicationOf {
        otherCase and (differentiator andThen not() andThen not())
    }

    return additionOf { zeroCaseTestFunction and otherCaseTestFunction }
}

fun boundedMuOperator(function: Function) =
    recursive(
        caseDifferentiation(
            boundedMuOperatorDifferentiationFunction(function),
            second() andThen s(),
            first()
        )
    ) withBaseCase zero()

inline fun boundedMuOperatorOf(function: Function, collector: () -> Array<Function>) =
    boundedMuOperator(function).of(collector)

internal fun boundedMuOperatorDifferentiationFunction(function: Function): Function {
    val firstTestArguments = Array<Function>(function.arity) { p(it + 1) }
    firstTestArguments[0] = second() andThen s()
    val firstTestFunction = function of { firstTestArguments }

    val secondTestArguments = firstTestArguments.clone()
    secondTestArguments[0] = zero()
    val secondTestFunction = function of { secondTestArguments }

    return additionOf {
        firstTestFunction and additionOf {
            first() and subtractionOf { one() and secondTestFunction }
        }
    }
}

//(x,y) -> ceiling(x / y)
fun ceilingDivision(): Function {
    //(n,x,y) -> x - n * y
    val g = subtractionOf { second() and multiplicationOf { first() and third() } }

    return boundedMuOperatorOf(g) { first() and first() and second() }
}

inline fun ceilingDivisionOf(collector: () -> Array<Function>) = ceilingDivision().of(collector)

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

inline fun floorDivisionOf(collector: () -> Array<Function>) = floorDivision().of(collector)

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

inline fun divisionOf(collector: () -> Array<Function>) = division().of(collector)

//WARNING Due to the nature of recursive functions using log will likely result in a StackOverflowError
//x -> logBase(x); if logBase(x) is a natural number
//x -> 0; else
fun log(base: Long): Function {
    val firstTestFunction = subtractionOf { second() and expOf { c(base) and first() } }

    val secondTestFunction = subtractionOf { expOf { c(base) and first() } and second() }

    val testFunction = additionOf { firstTestFunction and secondTestFunction }

    return boundedMuOperatorOf(testFunction) { first() and first() }
}
