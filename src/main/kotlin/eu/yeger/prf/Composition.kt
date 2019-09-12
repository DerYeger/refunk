package eu.yeger.prf

import eu.yeger.prf.exception.FunctionException

class Composition constructor(private val evaluator: Function, private vararg val functions: Function) : Function() {

    init {
        setRequiredArgumentCount(functions.map { it.requiredArgumentCount }.max() ?: 0)
    }

    @Throws(FunctionException::class)
    override fun evaluate(vararg arguments: Long): Long =
        evaluator.apply(*functions.map { it.apply(*arguments) }.toLongArray())
}
