package eu.yeger.refunk.base

import eu.yeger.refunk.exception.NaturalNumberException
import eu.yeger.refunk.exception.OverflowException

interface Argument {
    fun evaluated(): Long
}

internal class ArgumentFunction(private val function: Function, private val arguments: Array<Argument>) : Argument {
    private var result: Long? = null

    override fun evaluated(): Long {
        if (result == null) {
            result = function.applyArguments(arguments)
        }
        return result!!
    }
}

internal fun Function.asArgument(arguments: Array<Argument>, lazy: Boolean) =
    if (lazy)
        ArgumentFunction(this, arguments)
    else
        toNaturalNumber(this.applyArguments(arguments))

internal inline class NaturalNumber(private val value: Long) : Argument {
    override fun evaluated() = value
}

internal fun toNaturalNumber(value: Long) = if (value >= 0) NaturalNumber(value) else throw NaturalNumberException()

internal fun Argument.incremented() =
    if (this.evaluated() == Long.MAX_VALUE)
        throw OverflowException()
    else
        this.evaluated() + 1
