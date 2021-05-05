package eu.yeger.refunk.exception

internal class ArityException(message: String) : FunctionException(message) {

    internal constructor(
        arity: Int,
        argumentCount: Int
    ) : this("Function requires $arity argument(s) but received $argumentCount")
}
