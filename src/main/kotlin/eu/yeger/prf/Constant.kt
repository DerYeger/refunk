package eu.yeger.prf

class Constant(private val value: Long) : Function() {

    override fun evaluate(vararg arguments: Long): Long = value
}
