package eu.yeger.prf

import eu.yeger.prf.exception.ArityException
import eu.yeger.prf.exception.CompositionException
import eu.yeger.prf.exception.FunctionException

abstract class Function {

    var arity = 0

    @Throws(FunctionException::class)
    internal fun setArity(arity: Int) {
        if (arity < 0)
            throw ArityException("Function can not have a negative arity")

        this.arity = arity
    }

    @Throws(FunctionException::class)
    internal fun apply(vararg arguments: Long): Long =
        if (arity > arguments.size)
            throw ArityException(arity, arguments.size)
        else
            evaluate(*arguments)

    @Throws(FunctionException::class)
    fun compose(vararg functions: Function): Function =
        if (arity > functions.size)
            throw CompositionException(arity, functions.size)
        else
            Composition(this, *functions)

    @Throws(FunctionException::class)
    fun andThen(function: Function): Function =
        if (function.arity > 1)
            throw CompositionException("Function requires more than 1 argument")
        else
            function.compose(this)


    @Throws(FunctionException::class)
    protected abstract fun evaluate(vararg arguments: Long): Long
}
