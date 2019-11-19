package eu.yeger.refunk.base

fun Function.compose(vararg functions: Function, lazy: Boolean = false) = Composition(this, *functions, lazy = lazy)

infix fun Function.andThen(function: Function) = function.compose(this, lazy = true)

inline infix fun Function.of(collector: () -> Array<Function>) = Composition(this, *collector.invoke(), lazy = false)
infix fun Function.of(function: Function) = Composition(this, function, lazy = false)

infix fun Function.and(function: Function): Array<Function> = arrayOf(this, function)
infix fun Array<Function>.and(function: Function): Array<Function> = this.plus(function)

fun constant(value: Long) = Constant(value)
fun const(value: Long) = Constant(value)
val zero by lazy { Constant(0) }
val one by lazy { Constant(1) }

fun projection(index: Int) = Projection(index)
inline fun projectionOf(index: Int, collector: () -> Array<Function>) = Projection(index) of collector

val first by lazy { Projection(0) }
val second by lazy { Projection(1) }
val third by lazy { Projection(2) }
val fourth by lazy { Projection(3) }
val fifth by lazy { Projection(4) }
val sixth by lazy { Projection(5) }
val seventh by lazy { Projection(6) }
val eighth by lazy { Projection(7) }
val ninth by lazy { Projection(9) }
val tenth by lazy { Projection(10) }

val successor by lazy { Successor() }
fun successorOf(function: Function) = Successor() of function
