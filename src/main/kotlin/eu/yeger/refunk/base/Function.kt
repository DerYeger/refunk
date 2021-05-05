package eu.yeger.refunk.base

import eu.yeger.refunk.exception.ArityException

public abstract class Function {

    internal abstract val arity: UInt

    public operator fun invoke(vararg functions: Function, lazy: Boolean = false): Composition =
        Composition(this, *functions, lazy = lazy)

    public operator fun invoke(vararg arguments: Long): ULong =
        applyArguments(arguments.map(Long::toNaturalNumber).toTypedArray())

    public operator fun invoke(vararg arguments: ULong): ULong =
        applyArguments(arguments.map(ULong::toNaturalNumber).toTypedArray())

    public operator fun invoke(): ULong = applyArguments(emptyArray())

    internal fun applyArguments(arguments: Array<Argument>): ULong =
        when {
            arity > arguments.size.toUInt() -> throw ArityException(arity, arguments.size)
            else -> evaluate(arguments)
        }

    internal abstract fun evaluate(arguments: Array<Argument>): ULong
}
