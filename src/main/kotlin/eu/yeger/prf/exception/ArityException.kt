package eu.yeger.prf.exception

class ArityException : FunctionException {

    constructor(
        requiredArgumentCount: Int,
        receivedArgumentCount: Int
    ) : super("Function requires " + requiredArgumentCount + " argument" + (if (requiredArgumentCount != 1) "s" else "") + " but only received " + receivedArgumentCount)

    constructor(message: String) : super(message) {}
}
