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

    fun apply(vararg arguments: Long) = applyArguments(arguments.map { it.toNaturalNumber() }.toTypedArray())

    internal fun applyArguments(arguments: Array<Argument>) =
        when {
            arity > arguments.size -> throw ArityException(arity, arguments.size)
            else -> {
                val result = evaluate(arguments)
                if (result >= 0) result else throw NaturalNumberException()
            }
        }

    fun compose(vararg functions: Function, lazy: Boolean = false) = Composition(this, *functions, lazy = lazy)

    infix fun andThen(function: Function) =
        if (function.arity > 1)
            throw CompositionException("Function $function requires more than 1 argument")
        else
            function.compose(this, lazy = true)


    protected abstract fun evaluate(arguments: Array<Argument>): Long
}
