package eu.yeger.refunk.recursive

import eu.yeger.refunk.base.*
import eu.yeger.refunk.base.Function

//(x,y) -> x + y
fun addition(): Function = recursive { successor of recursionResult } withBaseCase recursionResult

val addition by lazy { addition() }

inline fun additionOf(arguments: () -> Array<Function>) = addition of arguments

//x -> x + value
fun add(value: Long) = additionOf { first and c(value) }

//x -> x - 1
fun predecessor() = recursive { recursionParameter } withBaseCase zero

val predecessor by lazy { predecessor() }

//(x,y) -> x - y
fun subtraction(): Function =
    recursive { predecessor of recursionResult } withBaseCase recursionResult of { second and first }

val subtraction by lazy { subtraction() }

inline fun subtractionOf(arguments: () -> Array<Function>) = subtraction of arguments

//x -> x - value
fun subtract(value: Long) = subtractionOf { first and c(value) }

//x -> value - x
fun subtractFrom(value: Long) = subtractionOf { c(value) and first }

fun not() = subtractFrom(1)

val not by lazy { not() }

//(x,y) -> x * y
fun multiplication() = recursive(additionOf { recursionResult and recursionArgument }) withBaseCase zero

val multiplication by lazy { multiplication() }

inline fun multiplicationOf(arguments: () -> Array<Function>) = multiplication of arguments

//x -> x * value
fun multiplyBy(value: Long) = multiplicationOf { first and c(value) }

//x -> x²
fun square() = multiplicationOf { first and first }

val square by lazy { square() }

//(x,y) -> x^y
fun exp() =
    recursive(multiplicationOf { recursionResult and recursionArgument }) withBaseCase one of { second and first }

val exp by lazy { exp() }

inline fun expOf(arguments: () -> Array<Function>) = exp of arguments

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
            successor of recursionParameter,
            recursionResult
        )
    ) withBaseCase zero

inline fun boundedMuOperatorOf(function: Function, arguments: () -> Array<Function>) =
    boundedMuOperator(function) of arguments

internal fun boundedMuOperatorDifferentiationFunction(function: Function): Function {
    val firstTestArguments = Array<Function>(function.arity) { p(it + 1) }
    firstTestArguments[0] = second andThen successor
    val firstTestFunction = function of { firstTestArguments }

    val secondTestArguments = firstTestArguments.clone()
    secondTestArguments[0] = zero
    val secondTestFunction = function of { secondTestArguments }

    return additionOf {
        firstTestFunction and additionOf {
            first and subtractionOf { one and secondTestFunction }
        }
    }
}

//(x,y) -> ceiling(x / y)
fun ceilingDivision(): Function {
    //(n,x,y) -> x - n * y
    val g = subtractionOf { second and multiplicationOf { first and third } }

    return boundedMuOperatorOf(g) { first and first and second }
}

val ceilingDivision by lazy { ceilingDivision() }

inline fun ceilingDivisionOf(arguments: () -> Array<Function>) = ceilingDivision of arguments

//(x,y) -> floor(x / y)
//or 0 if y == 0
fun floorDivision(): Function {
    val ceilingDivision = ceilingDivision()

    val differentiationFunction = subtractionOf {
        multiplicationOf { ceilingDivision and second } and first
    }

    return caseDifferentiation(
        differentiationFunction,
        ceilingDivision,
        ceilingDivision andThen predecessor()
    )
}

val floorDivision by lazy { floorDivision() }

inline fun floorDivisionOf(arguments: () -> Array<Function>) = floorDivision of arguments

//(x,y) -> x / y; if x / y is a natural number
//(x,y) -> 0; else
fun division(): Function {
    //(n,x,y) -> (x - n * y) + (n * y - x)
    val g = additionOf {
        subtractionOf {
            second and multiplicationOf { first and third }
        } and subtractionOf {
            multiplication() of { first and third } and second
        }
    }

    return boundedMuOperatorOf(g) { first and first and second }
}

val division by lazy { division() }

inline fun divisionOf(arguments: () -> Array<Function>) = division of arguments

//WARNING Due to the nature of recursive functions using log will likely result in a StackOverflowError
//x -> logBase(x); if logBase(x) is a natural number
//x -> 0; else
fun log(base: Long): Function {
    val firstTestFunction = subtractionOf { second and expOf { c(base) and first } }

    val secondTestFunction = subtractionOf { expOf { c(base) and first } and second }

    val testFunction = additionOf { firstTestFunction and secondTestFunction }

    return boundedMuOperatorOf(testFunction) { first and first }
}
