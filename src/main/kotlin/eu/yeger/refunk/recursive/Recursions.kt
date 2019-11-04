package eu.yeger.refunk.recursive

import eu.yeger.refunk.base.Function

class RecursionBuilder(val recursiveCase: Function)

inline fun recursive(provider: () -> Function) = RecursionBuilder(provider.invoke())

fun recursive(function: Function) = RecursionBuilder(function)

inline infix fun RecursionBuilder.withBaseCase(provider: () -> Function) =
    Recursion(provider.invoke(), this.recursiveCase)

infix fun RecursionBuilder.withBaseCase(base: Function) = Recursion(base, this.recursiveCase)