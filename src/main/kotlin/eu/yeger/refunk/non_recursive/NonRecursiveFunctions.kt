package eu.yeger.refunk.non_recursive

import eu.yeger.refunk.base.*
import eu.yeger.refunk.base.Function
import eu.yeger.refunk.exception.OverflowException
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.pow

public val addition: Function = object : Function() {
    override val arity = 2
    override fun evaluate(arguments: Array<Argument>) = arguments[0] + arguments[1]
}

public inline fun additionOf(arguments: () -> Array<Function>): Function = addition of arguments

public fun add(value: Long): Function = additionOf { first and constant(value) }

public val predecessor: Function = object : Function() {
    override val arity = 1
    override fun evaluate(arguments: Array<Argument>) = bounded(arguments[0].evaluated() - 1)
}

public val subtraction: Function = object : Function() {
    override val arity = 2
    override fun evaluate(arguments: Array<Argument>) = bounded(arguments[0].evaluated() - arguments[1].evaluated())
}

public inline fun subtractionOf(arguments: () -> Array<Function>): Function = subtraction of arguments

public fun subtract(value: Long): Function = subtractionOf { first and constant(value) }

public fun subtractFrom(value: Long): Function = subtractionOf { constant(value) and first }

public val not: Function = subtractFrom(1)

public val multiplication: Function = object : Function() {
    override val arity = 2
    override fun evaluate(arguments: Array<Argument>) = arguments[0] multiplyBy arguments[1]
}

public inline fun multiplicationOf(arguments: () -> Array<Function>): Function = multiplication of arguments

public fun multiplyBy(value: Long): Function = multiplicationOf { first and constant(value) }

public val square: Function = multiplicationOf { first and first }

public val exp: Function = object : Function() {
    override val arity = 2

    override fun evaluate(arguments: Array<Argument>): Long {
        val first = arguments[0].evaluated()
        val second = arguments[1].evaluated()
        if (second == 0L) return 1L
        val result = first.toDouble().pow(second.toDouble()).toLong()

        return if (log(result, first) == second)
            result
        else
            throw OverflowException()
    }
}

public inline fun expOf(arguments: () -> Array<Function>): Function = exp of arguments

public fun caseDifferentiation(
    differentiator: Function,
    zeroCase: Function,
    otherCase: Function
): Function = object : Function() {
    override val arity = maxOf(differentiator.arity, zeroCase.arity, otherCase.arity)

    override fun evaluate(arguments: Array<Argument>) = when (differentiator.applyArguments(arguments)) {
        0L -> zeroCase.applyArguments(arguments)
        else -> otherCase.applyArguments(arguments)
    }
}

public fun boundedMuOperator(function: Function): Function = object : Function() {
    override val arity = function.arity

    override fun evaluate(arguments: Array<Argument>): Long {
        for (x in 0..arguments[0].evaluated()) {
            if (function.applyArguments(
                    arrayOf(
                        toNaturalNumber(x),
                        *arguments
                            .slice(1 until arguments.size)
                            .toTypedArray()
                    )
                ) == 0L
            ) {
                return x
            }
        }
        return 0
    }
}

public inline fun boundedMuOperatorOf(function: Function, arguments: () -> Array<Function>): Function =
    boundedMuOperator(function).of(arguments)

public val ceilingDivision: Function = object : Function() {
    override val arity = 2

    override fun evaluate(arguments: Array<Argument>) =
        with(Pair(arguments[0].evaluated(), arguments[1].evaluated())) {
            if (second == 0L) return 0L
            ceil(first.toDouble() / second.toDouble()).toLong()
        }
}

public inline fun ceilingDivisionOf(arguments: () -> Array<Function>): Function = ceilingDivision of arguments

public val floorDivision: Function = object : Function() {
    override val arity = 2

    override fun evaluate(arguments: Array<Argument>) =
        with(Pair(arguments[0].evaluated(), arguments[1].evaluated())) {
            if (second == 0L) return 0L
            floor(first.toDouble() / second.toDouble()).toLong()
        }
}

public inline fun floorDivisionOf(arguments: () -> Array<Function>): Function = floorDivision of arguments

public val division: Function = object : Function() {
    override val arity = 2

    override fun evaluate(arguments: Array<Argument>): Long {
        val a = arguments[0].evaluated()
        val b = arguments[1].evaluated()

        return when {
            b == 0L -> 0
            a % b == 0L -> a / b
            else -> 0
        }
    }
}

public inline fun divisionOf(arguments: () -> Array<Function>): Function = division of arguments

public fun log(base: Long): Function = object : Function() {
    override val arity = 1
    override fun evaluate(arguments: Array<Argument>) = log(arguments[0].evaluated(), base)
}

private fun bounded(value: Long) = if (value >= 0) value else 0

private infix operator fun Argument.plus(other: Argument) = add(evaluated(), other.evaluated())

private fun add(first: Long, second: Long) =
    if (first + second < max(first, second))
        throw OverflowException()
    else
        first + second

private infix fun Argument.multiplyBy(other: Argument) = multiply(this.evaluated(), other.evaluated())

private fun multiply(first: Long, second: Long) =
    if (first == 0L || second == 0L)
        0L
    else if (first * second / first != second)
        throw OverflowException()
    else
        first * second

private fun log(x: Long, base: Long): Long {
    if (x < 0L || base <= 0L) return 0L

    val result = kotlin.math.log(x.toDouble(), base.toDouble())

    return when {
        result != floor(result) -> 0L
        else -> result.toLong()
    }
}
