package eu.yeger.prf

class Constant(val value: Long) : Function() {

    override fun evaluate(vararg arguments: Long): Long = value
}
