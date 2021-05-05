package eu.yeger.refunk.base

public object Successor : Function() {
    override val arity = 1U
    override fun evaluate(arguments: Array<Argument>) = arguments[0].incremented()
    override fun toString(): String = "Successor"
}
