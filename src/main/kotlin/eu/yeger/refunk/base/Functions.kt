package eu.yeger.refunk.base

public fun Function.compose(vararg functions: Function, lazy: Boolean = false): Composition =
    Composition(this, *functions, lazy = lazy)

public infix fun Function.andThen(function: Function): Composition = function.compose(this, lazy = true)

public inline infix fun Function.of(collector: () -> Array<Function>): Composition =
    compose(*collector.invoke(), lazy = false)

public infix fun Function.of(function: Function): Composition = Composition(this, function, lazy = false)

public infix fun Function.and(function: Function): Array<Function> = arrayOf(this, function)
public infix fun Array<Function>.and(function: Function): Array<Function> = this.plus(function)

public fun constant(value: Long): Constant = Constant(value.toNaturalNumber().evaluated())
public fun const(value: Long): Constant = Constant(value.toNaturalNumber().evaluated())
public val zero: Constant = Constant(0UL)
public val one: Constant = Constant(1UL)

public fun projection(index: Int): Projection = Projection(index)
public inline fun projectionOf(index: Int, collector: () -> Array<Function>): Composition =
    projection(index) of collector

public val first: Projection = Projection(0)
public val second: Projection = Projection(1)
public val third: Projection = Projection(2)
public val fourth: Projection = Projection(3)
public val fifth: Projection = Projection(4)
public val sixth: Projection = Projection(5)
public val seventh: Projection = Projection(6)
public val eighth: Projection = Projection(7)
public val ninth: Projection = Projection(8)
public val tenth: Projection = Projection(9)

public val successor: Successor = Successor
public fun successorOf(function: Function): Composition = Successor of function
