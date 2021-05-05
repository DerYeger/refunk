package eu.yeger.refunk.base

import eu.yeger.refunk.exception.NaturalNumberException
import eu.yeger.refunk.exception.OverflowException

internal interface Argument {
    val value: ULong
}

internal class ArgumentFunction(private val function: Function, private val arguments: Array<Argument>) : Argument {
    override val value: ULong by lazy {
        function.applyArguments(arguments)
    }
}

internal fun Function.asArgument(arguments: Array<Argument>, lazy: Boolean): Argument {
    return when {
        lazy -> ArgumentFunction(this, arguments)
        else -> applyArguments(arguments).toNaturalNumber()
    }
}


@JvmInline
internal value class NaturalNumber(override val value: ULong) : Argument

internal fun ULong.toNaturalNumber() = NaturalNumber(this)
internal fun Long.toNaturalNumber() = if (this >= 0) NaturalNumber(this.toULong()) else throw NaturalNumberException()

internal fun Argument.incremented(): ULong {
    return if (value == ULong.MAX_VALUE) {
        throw OverflowException()
    } else {
        value + 1UL
    }
}
