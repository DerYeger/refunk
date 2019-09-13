package eu.yeger.prf

import eu.yeger.prf.exception.FunctionException

class Composition constructor(private val evaluator: Function, private vararg val functions: Function) : Function() {

    init {
        setArity(functions.map { it.arity }.max() ?: 0)
    }

    @Throws(FunctionException::class)
    override fun evaluate(arguments: LongArray): Long =
        evaluator.applyArray(functions.map { it.applyArray(arguments) }.toLongArray())
}
