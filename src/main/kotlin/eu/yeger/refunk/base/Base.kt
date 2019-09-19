package eu.yeger.refunk.base

fun c(value: Long) = Constant(value)

fun p(index: Int) = Projection(index)

fun projectionOf(index: Int, collector: () -> Array<Function>) = Projection(index).of(collector)

fun s(): Successor = Successor()

fun successorOf(function: Function) = Successor().of(function)
