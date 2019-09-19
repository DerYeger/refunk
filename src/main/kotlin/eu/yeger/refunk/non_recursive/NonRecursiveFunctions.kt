package eu.yeger.refunk.non_recursive

import eu.yeger.refunk.base.*
import eu.yeger.refunk.base.Function
import eu.yeger.refunk.base.toNaturalNumber
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow

internal fun Long.bounded() = if (this >= 0) this else 0

fun addition() = object : Function() {
    init { arity = 2 }
    override fun evaluate(arguments: Array<Argument>) = (arguments[0].evaluated() + arguments[1].evaluated()).bounded()
}

inline fun additionOf(collector: () -> Array<Function>) = addition().of(collector)

fun add(value: Long) = additionOf { first() and c(value) }

fun predecessor() = object : Function() {
    init { arity = 1 }
    override fun evaluate(arguments: Array<Argument>) = (arguments[0].evaluated() - 1).bounded()
}

fun subtraction() = object : Function() {
    init { arity = 2 }
    override fun evaluate(arguments: Array<Argument>) = (arguments[0].evaluated() - arguments[1].evaluated()).bounded()
}

inline fun subtractionOf(collector: () -> Array<Function>) = subtraction().of(collector)

fun subtract(value: Long) = subtractionOf { first() and c(value) }

fun subtractFrom(value: Long) = subtractionOf { c(value) and first() }

fun not() = subtractFrom(1)

fun multiplication() = object : Function() {
    init { arity = 2 }

    override fun evaluate(arguments: Array<Argument>) = (arguments[0].evaluated() * arguments[1].evaluated()).bounded()
}

inline fun multiplicationOf(collector: () -> Array<Function>) = multiplication().of(collector)

fun multiplyBy(value: Long) =  multiplicationOf{ first() and c(value) }

fun square() =  multiplicationOf { first() and first() }

fun exp() = object : Function() {
    init { arity = 2 }
    override fun evaluate(arguments: Array<Argument>) =
        arguments[0].evaluated().toDouble().pow(arguments[1].evaluated().toDouble()).toLong().bounded()
}

inline fun expOf(collector: () -> Array<Function>) = exp().of(collector)

fun caseDifferentiation(
    differentiator: Function,
    zeroCase: Function,
    otherCase: Function
) = object : Function() {
    init { arity = maxOf(differentiator.arity, zeroCase.arity, otherCase.arity)}
    
    override fun evaluate(arguments: Array<Argument>) = when(differentiator.applyArguments(arguments)) {
        0L -> zeroCase.applyArguments(arguments)
        else -> otherCase.applyArguments(arguments)
    }
}

fun boundedMuOperator(function: Function) = object : Function() {
    init { arity = function.arity }

    override fun evaluate(arguments: Array<Argument>): Long {
        for (x in 0..arguments[0].evaluated()) {
            if (function.applyArguments(
                        arrayOf(
                            toNaturalNumber(x),
                            *arguments
                                .slice(1 until arguments.size)
                                .toTypedArray()
                        )
                ) == 0L) {
                return x
            }
        }
        return 0
    }
}

inline fun boundedMuOperatorOf(function: Function, collector: () -> Array<Function>) = boundedMuOperator(function).of(collector)

fun ceilingDivision() = object : Function() {
    init { arity = 2 }
    override fun evaluate(arguments: Array<Argument>) = with(Pair(arguments[0].evaluated(), arguments[1].evaluated())) {
        if (second == 0L) return 0L
        ceil(first.toDouble() / second.toDouble()).toLong().bounded()
    }
}

inline fun ceilingDivisionOf(collector: () -> Array<Function>) = ceilingDivision().of(collector)

fun floorDivision() = object : Function() {
    init { arity = 2 }
    override fun evaluate(arguments: Array<Argument>) = with(Pair(arguments[0].evaluated(), arguments[1].evaluated())) {
        if (second == 0L) return 0L
        floor(first.toDouble() / second.toDouble()).toLong().bounded()
    }
}

inline fun floorDivisionOf(collector: () -> Array<Function>) = floorDivision().of(collector)

fun division() = object : Function() {
    init { arity = 2 }
    override fun evaluate(arguments: Array<Argument>): Long {
        val a = arguments[0].evaluated()
        val b = arguments[1].evaluated()

        return when {
            b == 0L -> 0
            a % b == 0L -> (a / b).bounded()
            else -> 0
        }
    }
}

inline fun divisionOf(collector: () -> Array<Function>) = division().of(collector)

fun log(base: Long)= object : Function() {
    init { arity = 1 }
    override fun evaluate(arguments: Array<Argument>): Long {
        val x = arguments[0].evaluated()

        if (x < 0L || base <= 0L) return 0L

        val result = kotlin.math.log(x.toDouble(), base.toDouble())

        return when {
            result != floor(result) -> 0L
            else -> result.toLong()
        }
    }
}