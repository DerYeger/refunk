package eu.yeger.prf.non_recursive

import eu.yeger.prf.*
import eu.yeger.prf.Function
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow

internal fun Long.bounded() = if (this >= 0) this else 0

fun addition() = object : Function() {

    init { setArity(2) }

    override fun evaluate(arguments: Array<Argument>) = (arguments[0].evaluated() + arguments[1].evaluated()).bounded()
}

fun add(value: Long) = addition().compose(p(0), c(value))

fun predecessor() = object : Function() {
    init { setArity(1) }

    override fun evaluate(arguments: Array<Argument>) = (arguments[0].evaluated() - 1).bounded()
}

fun subtraction() = object : Function() {

    init { setArity(2) }

    override fun evaluate(arguments: Array<Argument>) = (arguments[0].evaluated() - arguments[1].evaluated()).bounded()
}

fun subtract(value: Long) = subtraction().compose(p(0), c(value))

fun subtractFrom(value: Long) = subtraction().compose(c(value), p(0))

fun multiplication() = object : Function() {

    init { setArity(2) }

    override fun evaluate(arguments: Array<Argument>) = (arguments[0].evaluated() * arguments[1].evaluated()).bounded()
}

fun multiplyBy(value: Long) = multiplication().compose(p(0), c(value))

fun square() = multiplication().compose(p(0), p(0))

fun exp() = object : Function() {
    init { setArity(2) }

    override fun evaluate(arguments: Array<Argument>) =
        arguments[0].evaluated().toDouble().pow(arguments[1].evaluated().toDouble()).toLong().bounded()
}

fun caseDifferentiation(
    differentiationFunction: Function,
    zeroCaseFunction: Function,
    otherCaseFunction: Function
): Function {
    val subtractFromOne = subtractFrom(1)

    val zeroCaseTestFunction = multiplication().compose(
        zeroCaseFunction,
        differentiationFunction
            .andThen(subtractFromOne)
    )

    val otherCaseTestFunction = multiplication().compose(
        otherCaseFunction,
        differentiationFunction
            .andThen(subtractFromOne)
            .andThen(subtractFromOne)
    )

    return addition().compose(
        zeroCaseTestFunction,
        otherCaseTestFunction
    )
}

fun boundedMuOperator(function: Function) = object : Function() {
    init { setArity(function.arity) }

    override fun evaluate(arguments: Array<Argument>): Long {
        for (x in 0..arguments[0].evaluated()) {
            if (function.applyArguments(
                        arrayOf(
                            x.toNaturalNumber(),
                            *arguments
                                .slice(1 until arguments.size)
                                .toTypedArray())
                ) == 0L) {
                return x
            }
        }
        return 0
    }
}

fun ceilingDivision() = object : Function() {
    init { setArity(2) }

    override fun evaluate(arguments: Array<Argument>) =
        ceil(arguments[0].evaluated().toDouble() / arguments[1].evaluated().toDouble()).toLong().bounded()
}

fun floorDivision() = object : Function() {
    init { setArity(2) }

    override fun evaluate(arguments: Array<Argument>) =
        floor(arguments[0].evaluated().toDouble() / arguments[1].evaluated().toDouble()).toLong().bounded()
}

fun division() = object : Function() {
    init { setArity(2) }

    override fun evaluate(arguments: Array<Argument>): Long {
        val a = arguments[0].evaluated()
        val b = arguments[1].evaluated()
        return if (a % b == 0L)
            (a / b).bounded()
        else
            0
    }
}

fun log(base: Long): Function {
    val firstTestFunction = subtraction().compose(
        p(1),
        exp().compose(
            c(base),
            p(0)
        )
    )

    val secondTestFunction = subtraction().compose(
        exp().compose(
            c(base),
            p(0)
        ),
        p(1)
    )

    return boundedMuOperator(
        addition().compose(
            firstTestFunction,
            secondTestFunction
        )
    ).compose(
        p(0),
        p(0)
    )
}