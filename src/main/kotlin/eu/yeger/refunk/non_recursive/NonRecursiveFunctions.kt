package eu.yeger.refunk.non_recursive

import eu.yeger.refunk.base.*
import eu.yeger.refunk.base.Function
import eu.yeger.refunk.exception.OverflowException
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.pow

public val addition: Function = object : Function() {
    override val arity = 2U
    override fun evaluate(arguments: Array<Argument>) = arguments[0] + arguments[1]
}

public inline fun additionOf(arguments: () -> Array<Function>): Function = addition of arguments

public fun add(value: Long): Function = additionOf { first and constant(value) }

public val predecessor: Function = object : Function() {
    override val arity = 1U
    override fun evaluate(arguments: Array<Argument>): ULong {
        val argument = arguments[0].value
        return if (argument > 0UL) {
            argument - 1UL
        } else {
            0UL
        }
    }
}

public val subtraction: Function = object : Function() {
    override val arity = 2U
    override fun evaluate(arguments: Array<Argument>): ULong {
        val minuend = arguments[0].value
        val subtrahend = arguments[1].value
        return if (minuend >= subtrahend) {
            minuend - subtrahend
        } else {
            0UL
        }
    }
}

public inline fun subtractionOf(arguments: () -> Array<Function>): Function = subtraction of arguments

public fun subtract(value: Long): Function = subtractionOf { first and constant(value) }

public fun subtractFrom(value: Long): Function = subtractionOf { constant(value) and first }

public val not: Function = subtractFrom(1)

public val multiplication: Function = object : Function() {
    override val arity = 2U
    override fun evaluate(arguments: Array<Argument>) = arguments[0] multiplyBy arguments[1]
}

public inline fun multiplicationOf(arguments: () -> Array<Function>): Function = multiplication of arguments

public fun multiplyBy(value: Long): Function = multiplicationOf { first and constant(value) }

public val square: Function = multiplicationOf { first and first }

public val exp: Function = object : Function() {
    override val arity = 2U

    override fun evaluate(arguments: Array<Argument>): ULong {
        val first = arguments[0].value
        val second = arguments[1].value
        if (second == 0UL) return 1UL
        val result = first.toDouble().pow(second.toDouble()).toULong()

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
        0UL -> zeroCase.applyArguments(arguments)
        else -> otherCase.applyArguments(arguments)
    }
}

public fun boundedMuOperator(function: Function): Function = object : Function() {
    override val arity = function.arity

    override fun evaluate(arguments: Array<Argument>): ULong {
        for (x in ULongRange(0UL, arguments[0].value)) {
            if (function.applyArguments(
                    arrayOf(
                        x.toNaturalNumber(),
                        *arguments
                            .slice(1 until arguments.size)
                            .toTypedArray()
                    )
                ) == 0UL
            ) {
                return x
            }
        }
        return 0UL
    }
}

public inline fun boundedMuOperatorOf(function: Function, arguments: () -> Array<Function>): Function =
    boundedMuOperator(function).of(arguments)

public val ceilingDivision: Function = object : Function() {
    override val arity = 2U

    override fun evaluate(arguments: Array<Argument>): ULong {
        return with(Pair(arguments[0].value, arguments[1].value)) {
            if (second == 0UL) return 0UL
            ceil(first.toDouble() / second.toDouble()).toULong()
        }
    }
}

public inline fun ceilingDivisionOf(arguments: () -> Array<Function>): Function = ceilingDivision of arguments

public val floorDivision: Function = object : Function() {
    override val arity = 2U

    override fun evaluate(arguments: Array<Argument>): ULong {
        return with(Pair(arguments[0].value, arguments[1].value)) {
            if (second == 0UL) return 0UL
            floor(first.toDouble() / second.toDouble()).toULong()
        }
    }
}

public inline fun floorDivisionOf(arguments: () -> Array<Function>): Function = floorDivision of arguments

public val division: Function = object : Function() {
    override val arity = 2U

    override fun evaluate(arguments: Array<Argument>): ULong {
        val a = arguments[0].value
        val b = arguments[1].value

        return when {
            b == 0UL -> 0UL
            a % b == 0UL -> a / b
            else -> 0UL
        }
    }
}

public inline fun divisionOf(arguments: () -> Array<Function>): Function = division of arguments

public fun log(base: Long): Function = object : Function() {
    override val arity = 1U
    override fun evaluate(arguments: Array<Argument>) =
        log(arguments[0].value, base.toNaturalNumber().value)
}

private infix operator fun Argument.plus(other: Argument) = add(value, other.value)

private fun add(first: ULong, second: ULong) =
    if (first + second < max(first, second))
        throw OverflowException()
    else
        first + second

private infix fun Argument.multiplyBy(other: Argument): ULong = multiply(this.value, other.value)

private fun multiply(first: ULong, second: ULong): ULong {
    return if (first == 0UL || second == 0UL) {
        0UL
    } else if (first * second / first != second) {
        throw OverflowException()
    } else {
        first * second
    }
}


private fun log(x: ULong, base: ULong): ULong {
    if (x < 0UL || base <= 0UL) return 0UL

    val result = kotlin.math.log(x.toDouble(), base.toDouble())

    return when {
        result != floor(result) -> 0UL
        else -> result.toULong()
    }
}
