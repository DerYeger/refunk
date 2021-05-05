package eu.yeger.refunk.recursive

import eu.yeger.refunk.base.*
import eu.yeger.refunk.base.Function

// (x,y) -> x + y
public val addition: Function = recursive { successor of recursionResult } withBaseCase firstBaseCaseArgument

public inline fun additionOf(arguments: () -> Array<Function>): Function = addition of arguments

// x -> x + value
public fun add(value: Long): Function = additionOf { first and constant(value) }

// x -> x - 1
public val predecessor: Function = recursive { recursionParameter } withBaseCase zero

// (x,y) -> x - y
public val subtraction: Function =
    recursive { predecessor of recursionResult } withBaseCase firstBaseCaseArgument of { second and first }

public inline fun subtractionOf(arguments: () -> Array<Function>): Function = subtraction of arguments

// x -> x - value
public fun subtract(value: Long): Function = subtractionOf { first and constant(value) }

// x -> value - x
public fun subtractFrom(value: Long): Function = subtractionOf { constant(value) and first }

public val not: Function = subtractFrom(1)

// (x,y) -> x * y
public val multiplication: Function =
    recursive(additionOf { recursionResult and firstRecursionArgument }) withBaseCase zero

public inline fun multiplicationOf(arguments: () -> Array<Function>): Function = multiplication of arguments

// x -> x * value
public fun multiplyBy(value: Long): Function = multiplicationOf { first and constant(value) }

// x -> x²
public val square: Function = multiplicationOf { first and first }

// (x,y) -> x^y
public val exp: Function =
    recursive {
        multiplicationOf { recursionResult and firstRecursionArgument }
    } withBaseCase {
        one
    } of { second and first }

public inline fun expOf(arguments: () -> Array<Function>): Function = exp of arguments

public fun caseDifferentiation(
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

public fun boundedMuOperator(function: Function): Function =
    recursive(
        caseDifferentiation(
            boundedMuOperatorDifferentiationFunction(function),
            successor of recursionParameter,
            recursionResult
        )
    ) withBaseCase zero

public inline fun boundedMuOperatorOf(function: Function, arguments: () -> Array<Function>): Function =
    boundedMuOperator(function) of arguments

internal fun boundedMuOperatorDifferentiationFunction(function: Function): Function {
    val firstTestArguments = Array<Function>(function.arity.toInt()) { projection(it + 1) }
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
public val ceilingDivision: Function = run {
    // (n,x,y) -> x - n * y
    val g = subtractionOf { second and multiplicationOf { first and third } }
    boundedMuOperatorOf(g) { first and first and second }
}

public inline fun ceilingDivisionOf(arguments: () -> Array<Function>): Function = ceilingDivision of arguments

// (x,y) -> floor(x / y)
// or 0 if y == 0
public val floorDivision: Function = run {
    val differentiationFunction = subtractionOf {
        multiplicationOf { ceilingDivision and second } and first
    }

    caseDifferentiation(
        differentiationFunction,
        ceilingDivision,
        ceilingDivision andThen predecessor
    )
}

public inline fun floorDivisionOf(arguments: () -> Array<Function>): Function = floorDivision of arguments

// (x,y) -> x / y; if x / y is a natural number
// (x,y) -> 0; else
public val division: Function = run {
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

public inline fun divisionOf(arguments: () -> Array<Function>): Function = division of arguments

// WARNING Due to the nature of recursive functions using log will likely result in a StackOverflowError
// x -> logBase(x); if logBase(x) is a natural number
// x -> 0; else
public fun log(base: Long): Function {
    val firstTestFunction = subtractionOf { second and expOf { constant(base) and first } }

    val secondTestFunction = subtractionOf { expOf { constant(base) and first } and second }

    val testFunction = additionOf { firstTestFunction and secondTestFunction }

    return boundedMuOperatorOf(testFunction) { first and first }
}
