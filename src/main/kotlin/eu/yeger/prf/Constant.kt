package eu.yeger.prf

class Constant constructor(val value: Long) : Function() {

    override fun evaluate(vararg arguments: Long): Long = value

}
