package eu.yeger.refunk.exception

class CompositionException(message: String) : FunctionException(message) {

    constructor(
        arity: Int,
        functionCount: Int
    ) : this("Composition requires $arity function(s) but received $functionCount")
}
