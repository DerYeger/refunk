package eu.yeger.prf.exception

class CompositionException : FunctionException {

    constructor(
        requiredFunctionCount: Int,
        receivedFunctionCount: Int
    ) : super("Composition requires $requiredFunctionCount functions but only received $receivedFunctionCount") {
    }

    constructor(message: String) : super(message) {}
}
