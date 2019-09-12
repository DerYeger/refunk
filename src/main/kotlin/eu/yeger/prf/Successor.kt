package eu.yeger.prf

class Successor : Function() {

    init {
        setArity(1)
    }

    override fun evaluate(vararg arguments: Long): Long = arguments[0] + 1
}
