package eu.yeger.prf

fun c(value: Long): Constant {
    return Constant(value)
}

fun p(index: Int): Projection {
    return Projection(index)
}

fun s(): Successor {
    return Successor()
}

//(x,y) -> x + y
fun addition(): Function {
    val first = p(0)
    return Recursion(
        first,
        first.andThen(s())
    )
}

//x -> x + value
fun add(value: Long): Function {
    return addition().compose(
        p(0),
        c(value)
    )
}

//x -> x - 1
fun predecessor(): Function {
    return Recursion(
        c(0),
        p(1)
    )
}

//(x,y) -> x - y
fun subtraction(): Function {
    val first = p(0)

    return Recursion(
        first,
        first.andThen(predecessor())
    ).compose(
        p(1),
        p(0)
    )
}

//x -> x - value
fun subtract(value: Long): Function {
    return subtraction().compose(
        p(0),
        c(value)
    )
}

//x -> value - x
fun subtractFrom(value: Long): Function {
    return subtraction().compose(
        c(value),
        p(0)
    )
}

//(x,y) -> x * y
fun multiplication(): Function {
    return Recursion(
        c(0),
        addition().compose(
            p(0),
            p(2)
        )
    )
}

//x -> x * value
fun multiplyBy(value: Long): Function {
    return multiplication().compose(
        p(0),
        c(value)
    )
}

//x -> x²
fun square(): Function {
    return multiplication().compose(
        p(0),
        p(0)
    )
}

//(x,y) -> x^y
fun exp(): Function {
    return Recursion(
        c(1),
        multiplication().compose(
            p(0),
            p(2)
        )
    ).compose(
        p(1),
        p(0)
    )
}

fun caseDifferentiation(
    differentiationFunction: Function,
    zeroCaseFunction: Function,
    otherCaseFunction: Function
): Function {
    val subtractFromOne = subtractFrom(1)

    val zeroCaseTestFunction = multiplication().compose(
        zeroCaseFunction,
        differentiationFunction
            .andThen(subtractFromOne)
    )

    val otherCaseTestFunction = multiplication().compose(
        otherCaseFunction,
        differentiationFunction
            .andThen(subtractFromOne)
            .andThen(subtractFromOne)
    )

    return addition().compose(
        zeroCaseTestFunction,
        otherCaseTestFunction
    )
}

fun boundedMuOperator(function: Function): Function {
    return Recursion(
        c(0),
        caseDifferentiation(
            boundedMuOperatorDifferentiationFunction(function),
            p(1).andThen(s()),
            p(0)
        )
    )
}

private fun boundedMuOperatorDifferentiationFunction(function: Function): Function {
    //first test function: function(m+1, x1, ..., xk)
    val firstTestArguments = Array<Function>(function.arity) { p(it + 1)}
    firstTestArguments[0] = p(1).andThen(s())
    val firstTestFunction = function.compose(*firstTestArguments)

    //second test function: boundedMuOperator(m, x1, ..., xk)
    val secondTestFunction = p(0)

    //third test function: function(0, x1, ..., xk)
    val thirdTestArguments = firstTestArguments.clone()
    thirdTestArguments[0] = c(0)
    val thirdTestFunction = function.compose(*thirdTestArguments)

    val add = addition()
    val sub = subtraction()

    //differentiationFunction is 0 if first and second test functions return 0 and the third test function does not
    return add.compose(
        firstTestFunction,
        add.compose(
            secondTestFunction,
            sub.compose(
                c(1),
                thirdTestFunction
            )
        )
    )
}

//(x,y) -> ceiling(x / y)
fun ceilingDivision(): Function {
    //(n,x,y) -> x - n * y
    val g = subtraction().compose(
        p(1),
        multiplication().compose(
            p(0),
            p(2)
        )
    )

    return boundedMuOperator(g).compose(
        p(0),
        p(0),
        p(1)
    )
}

//(x,y) -> floor(x / y)
//or 0 if y == 0
fun floorDivision(): Function {
    val ceilingDivision = ceilingDivision()

    val differentiationFunction = subtraction().compose(
        multiplication().compose(
            ceilingDivision,
            p(1)
        ),
        p(0)
    )

    return caseDifferentiation(
        differentiationFunction,
        ceilingDivision,
        ceilingDivision.andThen(predecessor())
    )
}

//(x,y) -> x / y; if x / y is a natural number
//(x,y) -> 0; else
fun division(): Function {
    //(n,x,y) -> (x - n * y) + (n * y - x)
    val g = addition().compose(
        subtraction().compose(
            p(1),
            multiplication().compose(
                p(0),
                p(2)
            )
        ),
        subtraction().compose(
            multiplication().compose(
                p(0),
                p(2)
            ),
            p(1)
        )
    )

    return boundedMuOperator(g).compose(
        p(0),
        p(0),
        p(1)
    )
}

//WARNING Due to the nature of recursive functions using log will likely result in a StackOverflowError
//x -> logBase(x); if logBase(x) is a natural number
//x -> 0; else
fun log(base: Long): Function {
    val firstTestFunction = subtraction().compose(
        p(1),
        exp().compose(
            c(base),
            p(0)
        )
    )

    val secondTestFunction = subtraction().compose(
        exp().compose(
            c(base),
            p(0)
        ),
        p(1)
    )

    return boundedMuOperator(
        addition().compose(
            firstTestFunction,
            secondTestFunction
        )
    ).compose(
        p(0),
        p(0)
    )
}
