package eu.yeger.refunk.base

fun Function.compose(vararg functions: Function, lazy: Boolean = false) = Composition(this, *functions, lazy = lazy)

infix fun Function.andThen(function: Function) = function.compose(this, lazy = true)

infix fun Function.of(collector: () -> Array<Function>) = Composition(this, *collector.invoke(), lazy = false)

infix fun Function.of(function: Function) = Composition(this, function, lazy = false)

infix fun Function.and(function: Function): Array<Function> = arrayOf(this, function)

infix fun Array<Function>.and(function: Function): Array<Function> = this.plus(function)

fun c(value: Long) = Constant(value)

fun p(index: Int) = Projection(index)

fun projectionOf(index: Int, collector: () -> Array<Function>) = Projection(index).of(collector)

fun s(): Successor = Successor()

fun successorOf(function: Function) = Successor().of(function)
