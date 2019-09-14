package eu.yeger.prf

class Composition constructor(private val evaluator: Function, private vararg val functions: Function) : Function() {

    init {
        setArity(functions.map { it.arity }.max() ?: 0)
    }

    override fun evaluate(arguments: LongArray): Long =
        evaluator.applyArray(functions.map { it.applyArray(arguments) }.toLongArray())
}
