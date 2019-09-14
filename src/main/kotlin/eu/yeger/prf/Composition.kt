package eu.yeger.prf

import eu.yeger.prf.exception.FunctionException

class Composition constructor(private val evaluator: Function, private vararg val functions: Function) : Function() {

    init {
        setArity(functions.map { it.arity }.max() ?: 0)
    }

    @Throws(FunctionException::class)
    override fun evaluate(arguments: Array<Argument>) =
        evaluator.applyArguments(
            functions
                .slice(0 until evaluator.arity)
                .map { it.applyArguments(arguments).toNaturalNumber() }
                .toTypedArray()
        )
}
