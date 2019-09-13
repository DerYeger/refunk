package eu.yeger.prf

class Successor : Function() {

    init {
        setArity(1)
    }

    override fun evaluate(arguments: LongArray): Long = arguments[0] + 1
}
