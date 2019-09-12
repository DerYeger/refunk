package eu.yeger.prf

class Constant constructor(private val value: Long) : Function() {

    override fun evaluate(vararg arguments: Long): Long {
        return value
    }
}
