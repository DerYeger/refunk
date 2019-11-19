package eu.yeger.refunk.base

import eu.yeger.refunk.exception.ArityException
import eu.yeger.refunk.exception.NaturalNumberException

abstract class Function {

    internal abstract val arity: Int

    operator fun invoke(vararg functions: Function) = Composition(this, *functions, lazy = false)

    operator fun invoke(vararg arguments: Long) = applyArguments(arguments.map { toNaturalNumber(it) }.toTypedArray())
    operator fun invoke() = applyArguments(emptyArray())

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
