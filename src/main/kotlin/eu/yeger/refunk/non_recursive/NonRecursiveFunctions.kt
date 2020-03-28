package eu.yeger.refunk.non_recursive

import eu.yeger.refunk.base.*
import eu.yeger.refunk.base.Function
import eu.yeger.refunk.exception.OverflowException
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.pow

val addition by lazy {
    object : Function() {
        override val arity = 2
        override fun evaluate(arguments: Array<Argument>) = arguments[0] + arguments[1]
    }
}

inline fun additionOf(arguments: () -> Array<Function>) = addition of arguments

fun add(value: Long) = additionOf { first and constant(value) }

val predecessor by lazy {
    object : Function() {
        override val arity = 1
        override fun evaluate(arguments: Array<Argument>) = bounded(arguments[0].evaluated() - 1)
    }
}

val subtraction by lazy {
    object : Function() {
        override val arity = 2
        override fun evaluate(arguments: Array<Argument>) = bounded(arguments[0].evaluated() - arguments[1].evaluated())
    }
}

inline fun subtractionOf(arguments: () -> Array<Function>) = subtraction of arguments

fun subtract(value: Long) = subtractionOf { first and constant(value) }

fun subtractFrom(value: Long) = subtractionOf { constant(value) and first }

val not by lazy { subtractFrom(1) }

val multiplication by lazy {
    object : Function() {
        override val arity = 2
        override fun evaluate(arguments: Array<Argument>) = arguments[0] multiplyBy arguments[1]
    }
}

inline fun multiplicationOf(arguments: () -> Array<Function>) = multiplication of arguments

fun multiplyBy(value: Long) = multiplicationOf { first and constant(value) }

val square by lazy { multiplicationOf { first and first } }

val exp by lazy {
    object : Function() {
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
}

inline fun expOf(arguments: () -> Array<Function>) = exp of arguments

fun caseDifferentiation(
    differentiator: Function,
    zeroCase: Function,
    otherCase: Function
) = object : Function() {
    override val arity = maxOf(differentiator.arity, zeroCase.arity, otherCase.arity)

    override fun evaluate(arguments: Array<Argument>) = when (differentiator.applyArguments(arguments)) {
        0L -> zeroCase.applyArguments(arguments)
        else -> otherCase.applyArguments(arguments)
    }
}

fun boundedMuOperator(function: Function) = object : Function() {
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

inline fun boundedMuOperatorOf(function: Function, arguments: () -> Array<Function>) =
    boundedMuOperator(function).of(arguments)

val ceilingDivision by lazy {
    object : Function() {
        override val arity = 2

        override fun evaluate(arguments: Array<Argument>) =
            with(Pair(arguments[0].evaluated(), arguments[1].evaluated())) {
                if (second == 0L) return 0L
                ceil(first.toDouble() / second.toDouble()).toLong()
            }
    }
}

inline fun ceilingDivisionOf(arguments: () -> Array<Function>) = ceilingDivision of arguments

val floorDivision by lazy {
    object : Function() {
        override val arity = 2

        override fun evaluate(arguments: Array<Argument>) =
            with(Pair(arguments[0].evaluated(), arguments[1].evaluated())) {
                if (second == 0L) return 0L
                floor(first.toDouble() / second.toDouble()).toLong()
            }
    }
}

inline fun floorDivisionOf(arguments: () -> Array<Function>) = floorDivision of arguments

val division by lazy {
    object : Function() {
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
}

inline fun divisionOf(arguments: () -> Array<Function>) = division of arguments

fun log(base: Long) = object : Function() {
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
