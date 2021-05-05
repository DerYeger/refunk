package eu.yeger.refunk.base

import eu.yeger.refunk.exception.ProjectionException

public class Projection(private val index: Int) : Function() {

    override val arity = index + 1

    init {
        if (index < 0) throw ProjectionException("Projection index can not be negative")
    }

    override fun evaluate(arguments: Array<Argument>) = arguments[index].evaluated()
}
