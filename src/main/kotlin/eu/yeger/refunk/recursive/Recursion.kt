package eu.yeger.refunk.recursive

import eu.yeger.refunk.base.Argument
import eu.yeger.refunk.base.Function
import eu.yeger.refunk.base.asArgument
import eu.yeger.refunk.non_recursive.bounded
import eu.yeger.refunk.base.toNaturalNumber
import kotlin.math.max

class Recursion(
    private val baseCaseFunction: Function,
    private val recursiveCaseFunction: Function,
    private val lazy: Boolean = false
) : Function() {

    init {
        setArity(
            max(
                baseCaseFunction.arity + 1,
                recursiveCaseFunction.arity - 1
            )
        )
    }

    override fun evaluate(arguments: Array<Argument>): Long = when(arguments[0].evaluated()) {
        0L -> baseCaseFunction.applyArguments(arguments.slice(1 until arguments.size).toTypedArray())
        else -> recursiveCaseFunction.applyArguments(recursiveCaseFunctionArguments(arguments))
    }

    private fun recursiveCaseFunctionArguments(arguments: Array<Argument>): Array<Argument> {
        //decrement the recursion parameter for the next recursive call
        val recursionArguments = arguments.clone()
        recursionArguments[0] = toNaturalNumber((recursionArguments[0].evaluated() - 1).bounded())

        return arrayOf(this.asArgument(recursionArguments, lazy), *recursionArguments)
    }
}
