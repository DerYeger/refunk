package eu.yeger.refunk.base

import eu.yeger.refunk.exception.NaturalNumberException

interface Argument {

    fun evaluated() : Long
}

internal class ArgumentFunction(private val function: Function, private val arguments: Array<Argument>) :
    Argument {

    override fun evaluated() = function.applyArguments(arguments)
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

internal fun Argument.incremented() = toNaturalNumber(this.evaluated() + 1)
