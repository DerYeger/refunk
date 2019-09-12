package eu.yeger.prf

import eu.yeger.prf.exception.FunctionException

class Successor : Function() {
    init {
        try {
            setArity(1)
        } catch (e: FunctionException) {
            e.printStackTrace()
        }

    }

    override fun evaluate(vararg arguments: Long): Long {
        return arguments[0] + 1
    }
}
