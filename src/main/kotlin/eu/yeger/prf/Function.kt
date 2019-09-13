package eu.yeger.prf

import eu.yeger.prf.exception.ArityException
import eu.yeger.prf.exception.CompositionException
import eu.yeger.prf.exception.NaturalNumberException

abstract class Function {

    var arity = 0

    internal fun setArity(arity: Int) {
        if (arity < 0)
            throw ArityException("Function $this can not have a negative arity")

        this.arity = arity
    }

    fun apply(vararg arguments: Long): Long =
        when {
            arity > arguments.size -> throw ArityException(arity, arguments.size)
            arguments.any { it < 0 } -> throw NaturalNumberException()
            else -> evaluate(arguments)
        }

    fun compose(vararg functions: Function): Function =
        if (arity > functions.size)
            throw CompositionException(arity, functions.size)
        else
            Composition(this, *functions)

    fun andThen(function: Function): Function =
        if (function.arity > 1)
            throw CompositionException("Function $function requires more than 1 argument")
        else
            function.compose(this)


    protected abstract fun evaluate(arguments: LongArray): Long
}
