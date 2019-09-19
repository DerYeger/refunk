package eu.yeger.refunk.base

import eu.yeger.refunk.exception.ProjectionException

class Projection(private val index: Int) : Function() {

    init {
        if (index < 0) throw ProjectionException("Projection index can not be negative")
        setArity(index + 1)
    }

    override fun evaluate(arguments: Array<Argument>) = arguments[index].evaluated()
}
