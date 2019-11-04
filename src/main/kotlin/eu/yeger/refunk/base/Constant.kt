package eu.yeger.refunk.base

import eu.yeger.refunk.exception.NaturalNumberException

class Constant(private val value: Long) : Function() {

    override val arity = 0

    init {
        if (value < 0) throw NaturalNumberException()
    }

    override fun evaluate(arguments: Array<Argument>) = value
}
