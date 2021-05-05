package eu.yeger.refunk.recursive

import eu.yeger.refunk.base.Argument
import eu.yeger.refunk.base.Function
import eu.yeger.refunk.base.asArgument
import eu.yeger.refunk.base.toNaturalNumber
import kotlin.math.max

public class Recursion(
    private val baseCase: Function,
    private val recursiveCase: Function,
    private val lazy: Boolean = true
) : Function() {

    override val arity = max(baseCase.arity + 1, recursiveCase.arity - 1)

    override fun evaluate(arguments: Array<Argument>): ULong = when (arguments[0].value) {
        0UL -> baseCase.applyArguments(arguments.slice(1 until arguments.size).toTypedArray())
        else -> recursiveCase.applyArguments(recursiveCaseFunctionArguments(arguments))
    }

    private fun recursiveCaseFunctionArguments(arguments: Array<Argument>): Array<Argument> {
        val recursionArguments = arguments.clone()
        recursionArguments[0] = (recursionArguments[0].value - 1UL).toNaturalNumber()

        return arrayOf(this.asArgument(recursionArguments, lazy), *recursionArguments)
    }
}
