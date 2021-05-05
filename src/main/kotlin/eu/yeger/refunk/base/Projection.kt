package eu.yeger.refunk.base

public data class Projection internal constructor(private val index: UInt) : Function() {
    override val arity = index + 1U
    override fun evaluate(arguments: Array<Argument>) = arguments[index.toInt()].value
}
