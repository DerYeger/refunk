package eu.yeger.prf

import eu.yeger.prf.exception.FunctionException

import kotlin.math.max

class Recursion constructor(private val baseCaseFunction: Function, private val recursiveCaseFunction: Function) :
    Function() {

    init {
        setArity(
            max(
                baseCaseFunction.arity + 1,
                recursiveCaseFunction.arity - 1
            )
        )
    }

    @Throws(FunctionException::class)
    override fun evaluate(vararg arguments: Long): Long {
        return if (arguments[0] == 0L) { //base case
            baseCaseFunction.apply(*arguments)
        } else { //recursive case
            recursiveCaseFunction.apply(*arguments)
        }
    }
}
