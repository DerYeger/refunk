package eu.yeger.refunk.base

public class Constant internal constructor(private val value: ULong) : Function() {
    override val arity = 0
    override fun evaluate(arguments: Array<Argument>) = value
}
