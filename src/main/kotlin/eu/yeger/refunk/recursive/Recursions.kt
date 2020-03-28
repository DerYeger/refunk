package eu.yeger.refunk.recursive

import eu.yeger.refunk.base.*
import eu.yeger.refunk.base.Function

val recursionResult by lazy { first }
val recursionParameter by lazy { second }

val firstRecursionArgument by lazy { third }
val secondRecursionArgument by lazy { fourth }
val thirdRecursionArgument by lazy { fifth }

val firstBaseCaseArgument by lazy { first }
val secondBaseCaseArgument by lazy { second }
val thirdBaseCaseArgument by lazy { third }

class RecursionBuilder(val recursiveCase: Function)

inline fun recursive(function: () -> Function) = RecursionBuilder(function())

fun recursive(function: Function) = RecursionBuilder(function)

inline infix fun RecursionBuilder.withBaseCase(function: () -> Function) =
    Recursion(function(), this.recursiveCase)

infix fun RecursionBuilder.withBaseCase(base: Function) = Recursion(base, this.recursiveCase)
