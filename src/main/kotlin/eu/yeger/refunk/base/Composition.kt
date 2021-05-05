package eu.yeger.refunk.base

import eu.yeger.refunk.exception.CompositionException

public class Composition
internal constructor(
    private val evaluator: Function,
    private vararg val functions: Function,
    private val lazy: Boolean = false
) : Function() {

    override val arity = functions.map(Function::arity).maxOrNull() ?: 0U

    init {
        if (evaluator.arity != functions.size.toUInt()) throw CompositionException(evaluator, functions.size)
    }

    override fun evaluate(arguments: Array<Argument>) =
        evaluator.applyArguments(
            functions
                .map { it.asArgument(arguments, lazy) }
                .toTypedArray()
        )
}
