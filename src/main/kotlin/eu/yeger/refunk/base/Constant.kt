package eu.yeger.refunk.base

public data class Constant internal constructor(private val value: ULong) : Function() {
    override val arity = 0U
    override fun evaluate(arguments: Array<Argument>) = value
}
