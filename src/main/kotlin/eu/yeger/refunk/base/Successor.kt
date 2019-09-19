package eu.yeger.refunk.base

class Successor : Function() {

    init {
        setArity(1)
    }

    override fun evaluate(arguments: Array<Argument>) = arguments[0].incremented().evaluated()
}
