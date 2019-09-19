package eu.yeger.refunk.exception

class ArityException(message: String) : FunctionException(message) {

    constructor(
        arity: Int,
        argumentCount: Int
    ) : this("Function requires $arity argument(s) but received $argumentCount")
}
