package eu.yeger.prf

import eu.yeger.prf.exception.ArityException
import eu.yeger.prf.exception.CompositionException
import eu.yeger.prf.exception.NaturalNumberException

abstract class Function {

    var arity = 0

    internal fun setArity(arity: Int) {
        if (arity < 0)
            throw ArityException("Function can not have a negative arity")

        this.arity = arity
    }

    internal fun apply(vararg arguments: Long): Long =
        if (arity > arguments.size)
            throw ArityException(arity, arguments.size)
        else if (arguments.any { it < 0 })
            throw NaturalNumberException("Primitive recursive functions are only defined for natural numbers")
        else
            evaluate(*arguments)

    fun compose(vararg functions: Function): Function =
        if (arity > functions.size)
            throw CompositionException(arity, functions.size)
        else
            Composition(this, *functions)

    fun andThen(function: Function): Function =
        if (function.arity > 1)
            throw CompositionException("Function requires more than 1 argument")
        else
            function.compose(this)


    protected abstract fun evaluate(vararg arguments: Long): Long
}
