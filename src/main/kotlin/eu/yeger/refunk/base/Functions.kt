package eu.yeger.refunk.base

import eu.yeger.refunk.exception.ProjectionException

public fun Function.compose(vararg functions: Function, lazy: Boolean = false): Composition =
    Composition(this, *functions, lazy = lazy)

public infix fun Function.andThen(function: Function): Composition = function.compose(this, lazy = true)

public inline infix fun Function.of(collector: () -> Array<Function>): Composition =
    compose(*collector.invoke(), lazy = false)

public infix fun Function.of(function: Function): Composition = Composition(this, function, lazy = false)

public infix fun Function.and(function: Function): Array<Function> = arrayOf(this, function)
public infix fun Array<Function>.and(function: Function): Array<Function> = this.plus(function)

public fun constant(value: Long): Constant = Constant(value.toNaturalNumber().value)
public fun const(value: Long): Constant = Constant(value.toNaturalNumber().value)
public val zero: Constant = Constant(0UL)
public val one: Constant = Constant(1UL)

public fun projection(index: Int): Projection = if (index < 0) throw ProjectionException("Projection index can not be negative") else Projection(index.toUInt())
public inline fun projectionOf(index: Int, collector: () -> Array<Function>): Composition =
    projection(index) of collector

public val first: Projection = Projection(0U)
public val second: Projection = Projection(1U)
public val third: Projection = Projection(2U)
public val fourth: Projection = Projection(3U)
public val fifth: Projection = Projection(4U)
public val sixth: Projection = Projection(5U)
public val seventh: Projection = Projection(6U)
public val eighth: Projection = Projection(7U)
public val ninth: Projection = Projection(8U)
public val tenth: Projection = Projection(9U)

public val successor: Successor = Successor
public fun successorOf(function: Function): Composition = Successor of function
