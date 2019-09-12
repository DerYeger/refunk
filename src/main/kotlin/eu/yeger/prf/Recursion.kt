package eu.yeger.prf

import eu.yeger.prf.exception.FunctionException
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
        0L -> baseCaseFunction.apply(*arguments.slice(1 until arguments.size).toLongArray())
        else -> recursiveCaseFunction.apply(*recursiveCaseFunctionArguments(arguments))
    }

    @Throws(FunctionException::class)
    private fun recursiveCaseFunctionArguments(arguments: LongArray): LongArray {
        //decrement the recursion parameter for the next recursive call
        val recursionArguments = arguments.clone()
        recursionArguments[0] = recursionArguments[0] - 1L

        //adds the recursion result to the parameters for the recursive case function
        return longArrayOf(apply(*recursionArguments), *recursionArguments)
    }
}
