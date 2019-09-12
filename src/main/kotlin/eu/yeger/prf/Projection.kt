package eu.yeger.prf

import eu.yeger.prf.exception.ProjectionException

class Projection(private val index: Int) : Function() {

    init {
        if (index < 1) {
            throw ProjectionException("Projection expects index of at least 1 but received 0")
        }

        setRequiredArgumentCount(index)
    }

    override fun evaluate(vararg arguments: Long): Long {
        return arguments[index - 1]
    }
}
