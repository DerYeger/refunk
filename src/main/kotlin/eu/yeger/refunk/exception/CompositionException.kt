package eu.yeger.refunk.exception

import eu.yeger.refunk.base.Function

class CompositionException(message: String) : FunctionException(message) {

    constructor(
        function: Function,
        functionCount: Int
    ) : this("Function $function is of arity ${function.arity} but composition provided $functionCount function(s)")
}
