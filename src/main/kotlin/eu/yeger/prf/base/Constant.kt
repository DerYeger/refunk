package eu.yeger.prf.base

import eu.yeger.prf.exception.NaturalNumberException

class Constant(private val value: Long) : Function() {

    init {
        if (value < 0) throw NaturalNumberException()
    }

    override fun evaluate(arguments: Array<Argument>) = value
}
