package eu.yeger.prf

import eu.yeger.prf.exception.NaturalNumberException

interface Argument {

    fun evaluated() : Long
}

internal class ArgumentFunction(private val function: Function, private val arguments: Array<Argument>) : Argument {

    override fun evaluated() = function.applyArguments(arguments)
}

internal fun Function.asArgument(arguments: Array<Argument>, lazy: Boolean) =
    if (lazy)
        ArgumentFunction(this, arguments)
    else
        this.applyArguments(arguments).toNaturalNumber()

internal inline class NaturalNumber(private val value: Long) : Argument {

    override fun evaluated() = value
}

internal fun Long.toNaturalNumber() = if (this >= 0) NaturalNumber(this) else throw NaturalNumberException()

internal fun Argument.incremented() = (this.evaluated() + 1).toNaturalNumber()
