package eu.yeger.refunk.recursive

import eu.yeger.refunk.base.Function
import eu.yeger.refunk.base.first
import eu.yeger.refunk.base.second
import eu.yeger.refunk.base.third

val recursionResult by lazy { first }

val recursionParameter by lazy { second }

//projects the FIRST recursion argument
val recursionArgument by lazy { third }

class RecursionBuilder(val recursiveCase: Function)

inline fun recursive(function: () -> Function) = RecursionBuilder(function())

fun recursive(function: Function) = RecursionBuilder(function)

inline infix fun RecursionBuilder.withBaseCase(function: () -> Function) =
    Recursion(function(), this.recursiveCase)

infix fun RecursionBuilder.withBaseCase(base: Function) = Recursion(base, this.recursiveCase)