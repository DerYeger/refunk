package eu.yeger.prf

import eu.yeger.prf.exception.ArityException
import eu.yeger.prf.exception.CompositionException
import eu.yeger.prf.exception.FunctionException

abstract class Function {

    var requiredArgumentCount = 0

    @Throws(FunctionException::class)
    internal fun setRequiredArgumentCount(requiredArgumentCount: Int) {
        if (requiredArgumentCount < 0) {
            throw ArityException("Function can not have a negative arity")
        }

        this.requiredArgumentCount = requiredArgumentCount
    }

    @Throws(FunctionException::class)
    internal fun apply(vararg arguments: Long): Long =
        if (requiredArgumentCount > arguments.size)
            throw ArityException(requiredArgumentCount, arguments.size)
        else
            evaluate(*arguments)

    @Throws(FunctionException::class)
    fun compose(vararg functions: Function): Function =
        if (requiredArgumentCount > functions.size)
            throw CompositionException(requiredArgumentCount, functions.size)
        else
            Composition(this, *functions)

    @Throws(FunctionException::class)
    fun andThen(function: Function): Function =
        if (function.requiredArgumentCount > 1)
            throw CompositionException("Function requires more than 1 argument")
        else
            function.compose(this)


    @Throws(FunctionException::class)
    protected abstract fun evaluate(vararg arguments: Long): Long
}
