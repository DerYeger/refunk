package eu.yeger.refunk.recursive

import eu.yeger.refunk.base.*
import eu.yeger.refunk.base.Function

public val recursionResult: Projection = first
public val recursionParameter: Projection = second

public val firstRecursionArgument: Projection = third
public val secondRecursionArgument: Projection = fourth
public val thirdRecursionArgument: Projection = fifth

public val firstBaseCaseArgument: Projection = first
public val secondBaseCaseArgument: Projection = second
public val thirdBaseCaseArgument: Projection = third

public data class RecursionBuilder(val recursiveCase: Function)

public inline fun recursive(function: () -> Function): RecursionBuilder = RecursionBuilder(function())

public fun recursive(function: Function): RecursionBuilder = RecursionBuilder(function)

public inline infix fun RecursionBuilder.withBaseCase(function: () -> Function): Recursion =
    Recursion(function(), this.recursiveCase)

public infix fun RecursionBuilder.withBaseCase(base: Function): Recursion = Recursion(base, this.recursiveCase)
