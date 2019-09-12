package eu.yeger.prf

import kotlin.math.max

class Recursion(private val baseCaseFunction: Function, private val recursiveCaseFunction: Function) : Function() {

    init {
        setArity(
            max(
                baseCaseFunction.arity + 1,
                recursiveCaseFunction.arity - 1
            )
        )
    }

    override fun evaluate(vararg arguments: Long): Long = when(arguments[0]) {
        0L -> baseCaseFunction.apply(*arguments)
        else -> recursiveCaseFunction.apply(*arguments)
    }
}
