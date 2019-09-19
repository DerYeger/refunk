package eu.yeger.refunk.base

import eu.yeger.refunk.exception.ArityException
import eu.yeger.refunk.exception.CompositionException
import eu.yeger.refunk.exception.NaturalNumberException

abstract class Function {

    var arity = 0

    internal fun setArity(arity: Int) {
        if (arity < 0)
            throw ArityException("Function $this can not have a negative arity")

        this.arity = arity
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

    fun compose(vararg functions: Function, lazy: Boolean = false) =
        Composition(this, *functions, lazy = lazy)

    infix fun of(collector: () -> Array<Function>) =
        Composition(this, *collector.invoke(), lazy = false)

    infix fun of(function: Function) =
        Composition(this, function, lazy = false)

    infix fun and(function: Function): Array<Function> = arrayOf(this, function)

    infix fun andThen(function: Function) =
        if (function.arity > 1)
            throw CompositionException("Parameter $function is not a unary function")
        else
            function.compose(this, lazy = true)

    protected abstract fun evaluate(arguments: Array<Argument>): Long
}

infix fun Array<Function>.and(function: Function): Array<Function> = arrayOf(*this, function)
