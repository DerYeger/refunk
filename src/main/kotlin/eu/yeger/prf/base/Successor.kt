package eu.yeger.prf.base

import eu.yeger.prf.base.Argument
import eu.yeger.prf.base.Function
import eu.yeger.prf.base.incremented

class Successor : Function() {

    init {
        setArity(1)
    }

    override fun evaluate(arguments: Array<Argument>) = arguments[0].incremented().evaluated()
}
