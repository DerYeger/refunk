package eu.yeger.refunk.base

import eu.yeger.refunk.exception.ArityException
import eu.yeger.refunk.exception.NaturalNumberException

public abstract class Function {

    internal abstract val arity: Int

    public operator fun invoke(vararg functions: Function, lazy: Boolean = false): Composition =
        Composition(this, *functions, lazy = lazy)

    public operator fun invoke(vararg arguments: Long): Long =
        applyArguments(arguments.map { toNaturalNumber(it) }.toTypedArray())

    public operator fun invoke(): Long = applyArguments(emptyArray())

    internal fun applyArguments(arguments: Array<Argument>): Long =
        when {
            arity > arguments.size -> throw ArityException(arity, arguments.size)
            else -> {
                val result = evaluate(arguments)
                if (result >= 0) result else throw NaturalNumberException()
            }
        }

    internal abstract fun evaluate(arguments: Array<Argument>): Long
}
