package eu.yeger.refunk.base

import eu.yeger.refunk.exception.ArityException
import eu.yeger.refunk.exception.CompositionException
import eu.yeger.refunk.exception.NaturalNumberException

abstract class Function {

    internal var arity = 0
        internal set(value) {
            if (value >= 0)
                field = value
            else
                throw ArityException("Function $this can not have a negative arity")
        }

    fun apply(vararg arguments: Long) = applyArguments(arguments.map { toNaturalNumber(it) }.toTypedArray())

    internal fun applyArguments(arguments: Array<Argument>) =
        when {
            arity > arguments.size -> throw ArityException(arity, arguments.size)
            else -> {
                val result = evaluate(arguments)
                if (result >= 0) result else throw NaturalNumberException()
            }
        }

    protected abstract fun evaluate(arguments: Array<Argument>): Long
}
