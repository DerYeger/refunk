package eu.yeger.refunk.base

import eu.yeger.refunk.exception.NaturalNumberException
import eu.yeger.refunk.exception.OverflowException

internal interface Argument {
    fun evaluated(): ULong
}

internal class ArgumentFunction(private val function: Function, private val arguments: Array<Argument>) : Argument {
    private var result: ULong? = null

    override fun evaluated(): ULong {
        if (result == null) {
            result = function.applyArguments(arguments)
        }
        return result!!
    }
}

internal fun Function.asArgument(arguments: Array<Argument>, lazy: Boolean): Argument {
    return when {
        lazy -> ArgumentFunction(this, arguments)
        else -> applyArguments(arguments).toNaturalNumber()
    }
}


@JvmInline
internal value class NaturalNumber(private val value: ULong) : Argument {
    override fun evaluated() = value
}

internal fun ULong.toNaturalNumber() = NaturalNumber(this)
internal fun Long.toNaturalNumber() = if (this >= 0) NaturalNumber(this.toULong()) else throw NaturalNumberException()

internal fun Argument.incremented(): ULong {
    val argument = evaluated()
    return if (argument == ULong.MAX_VALUE) {
        throw OverflowException()
    } else {
        argument + 1UL
    }
}
