package eu.yeger.prf.exception

class ArityException(message: String) : FunctionException(message) {

    constructor(
        arity: Int,
        argumentCount: Int
    ) : this("Function requires $arity argument(s) but only received $argumentCount")
}
