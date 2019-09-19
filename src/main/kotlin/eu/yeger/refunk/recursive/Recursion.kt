package eu.yeger.refunk.recursive

import eu.yeger.refunk.base.*
import eu.yeger.refunk.base.Function
import eu.yeger.refunk.base.asArgument
import eu.yeger.refunk.base.toNaturalNumber
import kotlin.math.max

class Recursion(
    private val baseCase: Function,
    private val recursiveCase: Function,
    private val lazy: Boolean = false
) : Function() {

    init {
        arity = max(baseCase.arity + 1, recursiveCase.arity - 1)
    }

    override fun evaluate(arguments: Array<Argument>): Long = when(arguments[0].evaluated()) {
        0L -> baseCase.applyArguments(arguments.slice(1 until arguments.size).toTypedArray())
        else -> recursiveCase.applyArguments(recursiveCaseFunctionArguments(arguments))
    }

    private fun recursiveCaseFunctionArguments(arguments: Array<Argument>): Array<Argument> {
        val recursionArguments = arguments.clone()
        recursionArguments[0] = toNaturalNumber(recursionArguments[0].evaluated() - 1)

        return arrayOf(this.asArgument(recursionArguments, lazy), *recursionArguments)
    }
}
