package eu.yeger.refunk.base

import eu.yeger.refunk.exception.CompositionException

public class Composition
internal constructor(
    private val evaluator: Function,
    private vararg val functions: Function,
    private val lazy: Boolean = false
) : Function() {

    override val arity = functions.map { it.arity }.maxOrNull() ?: 0

    init {
        if (evaluator.arity != functions.size) throw CompositionException(evaluator, functions.size)
    }

    override fun evaluate(arguments: Array<Argument>) =
        evaluator.applyArguments(
            functions
                .map { it.asArgument(arguments, lazy) }
                .toTypedArray()
        )
}
