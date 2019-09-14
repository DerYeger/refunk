package eu.yeger.prf

import eu.yeger.prf.exception.NaturalNumberException

interface Argument {

    fun evaluated() : Long
}

internal class ArgumentFunction(private val function: Function, private val arguments: Array<Argument>) : Argument {

    override fun evaluated(): Long = function.applyArguments(arguments)
}

internal fun Function.asArgument(arguments: Array<Argument>) = ArgumentFunction(this, arguments)

internal inline class NaturalNumber(private val value: Long) : Argument {

    override fun evaluated(): Long = value
}

internal fun Long.toNaturalNumber() = if (this >= 0) NaturalNumber(this) else throw NaturalNumberException()
