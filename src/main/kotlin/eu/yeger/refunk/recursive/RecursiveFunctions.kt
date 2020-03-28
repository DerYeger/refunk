package eu.yeger.refunk.recursive

import eu.yeger.refunk.base.*
import eu.yeger.refunk.base.Function

// (x,y) -> x + y
val addition by lazy {
    recursive { successor of recursionResult } withBaseCase firstBaseCaseArgument
}

inline fun additionOf(arguments: () -> Array<Function>) = addition of arguments

// x -> x + value
fun add(value: Long) = additionOf { first and constant(value) }

// x -> x - 1
val predecessor by lazy {
    recursive { recursionParameter } withBaseCase zero
}

// (x,y) -> x - y
val subtraction by lazy {
    recursive { predecessor of recursionResult } withBaseCase firstBaseCaseArgument of { second and first }
}

inline fun subtractionOf(arguments: () -> Array<Function>) = subtraction of arguments

// x -> x - value
fun subtract(value: Long) = subtractionOf { first and constant(value) }

// x -> value - x
fun subtractFrom(value: Long) = subtractionOf { constant(value) and first }

val not by lazy { subtractFrom(1) }

// (x,y) -> x * y
val multiplication by lazy {
    recursive(additionOf { recursionResult and firstRecursionArgument }) withBaseCase zero
}

inline fun multiplicationOf(arguments: () -> Array<Function>) = multiplication of arguments

// x -> x * value
fun multiplyBy(value: Long) = multiplicationOf { first and constant(value) }

// x -> x²
val square by lazy {
    multiplicationOf { first and first }
}

// (x,y) -> x^y
val exp by lazy {
    recursive {
        multiplicationOf { recursionResult and firstRecursionArgument }
    } withBaseCase {
        one
    } of { second and first }
}

inline fun expOf(arguments: () -> Array<Function>) = exp of arguments

fun caseDifferentiation(
    differentiator: Function,
    zeroCase: Function,
    otherCase: Function
): Function {
    val zeroCaseTestFunction = multiplicationOf {
        zeroCase and (differentiator andThen not)
    }

    val otherCaseTestFunction = multiplicationOf {
        otherCase and (differentiator andThen not andThen not)
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
    val firstTestArguments = Array<Function>(function.arity) { projection(it + 1) }
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

// (x,y) -> ceiling(x / y)
val ceilingDivision by lazy {
    // (n,x,y) -> x - n * y
    val g = subtractionOf { second and multiplicationOf { first and third } }
    boundedMuOperatorOf(g) { first and first and second }
}

inline fun ceilingDivisionOf(arguments: () -> Array<Function>) = ceilingDivision of arguments

// (x,y) -> floor(x / y)
// or 0 if y == 0
val floorDivision by lazy {
    val differentiationFunction = subtractionOf {
        multiplicationOf { ceilingDivision and second } and first
    }

    caseDifferentiation(
        differentiationFunction,
        ceilingDivision,
        ceilingDivision andThen predecessor
    )
}

inline fun floorDivisionOf(arguments: () -> Array<Function>) = floorDivision of arguments

// (x,y) -> x / y; if x / y is a natural number
// (x,y) -> 0; else
val division by lazy {
    // (n,x,y) -> (x - n * y) + (n * y - x)
    val g = additionOf {
        subtractionOf {
            second and multiplicationOf { first and third }
        } and subtractionOf {
            multiplication of { first and third } and second
        }
    }

    boundedMuOperatorOf(g) { first and first and second }
}

inline fun divisionOf(arguments: () -> Array<Function>) = division of arguments

// WARNING Due to the nature of recursive functions using log will likely result in a StackOverflowError
// x -> logBase(x); if logBase(x) is a natural number
// x -> 0; else
fun log(base: Long): Function {
    val firstTestFunction = subtractionOf { second and expOf { constant(base) and first } }

    val secondTestFunction = subtractionOf { expOf { constant(base) and first } and second }

    val testFunction = additionOf { firstTestFunction and secondTestFunction }

    return boundedMuOperatorOf(testFunction) { first and first }
}
