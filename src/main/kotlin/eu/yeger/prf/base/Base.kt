package eu.yeger.prf.base

fun c(value: Long) = Constant(value)

fun p(index: Int) = Projection(index)

fun projectionOf(index: Int, collector: () -> Array<Function>) = Projection(index).of(collector)

fun s(): Successor = Successor()

fun successorOf(collector: () -> Array<Function>) = Successor().of(collector)
