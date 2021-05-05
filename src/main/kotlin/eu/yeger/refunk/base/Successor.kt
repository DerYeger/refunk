package eu.yeger.refunk.base

public class Successor : Function() {

    override val arity = 1

    override fun evaluate(arguments: Array<Argument>) = arguments[0].incremented()
}
