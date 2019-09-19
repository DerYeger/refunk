package eu.yeger.refunk.recursive

import eu.yeger.refunk.base.Function
import eu.yeger.refunk.base.of

inline fun recursionOf(baseCase: Function,
                       recursiveCase: Function,
                       collector: () -> Array<Function>) = Recursion(baseCase, recursiveCase).of(collector)

class RecursionBuilder(val recursiveCase: Function)

inline infix fun RecursionBuilder.withBaseCase(provider: () -> Function) = Recursion(provider.invoke(), this.recursiveCase)

infix fun RecursionBuilder.withBaseCase(base: Function) = Recursion(base, this.recursiveCase)

inline fun recursive(provider: () -> Function) = RecursionBuilder(provider.invoke())

fun recursive(function: Function) = RecursionBuilder(function)