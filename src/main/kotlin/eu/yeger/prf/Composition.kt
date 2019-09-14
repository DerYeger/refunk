package eu.yeger.prf

import kotlin.math.min

class Composition constructor(
    private val evaluator: Function,
    private vararg val functions: Function,
    private val lazy: Boolean = false
    ) : Function() {

    init {
        setArity(functions.map { it.arity }.max() ?: 0)
    }

    override fun evaluate(arguments: Array<Argument>) =
        evaluator.applyArguments(
            functions
                .slice(0 until min(evaluator.arity, functions.size))
                .map { it.asArgument(arguments, lazy) }
                .toTypedArray()
        )
}
