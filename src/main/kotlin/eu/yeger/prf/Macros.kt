package eu.yeger.prf

import eu.yeger.prf.exception.FunctionException

@Suppress("Never used")
object Macros {

    fun c(value: Long): Function {
        return Constant(value)
    }

    @Throws(FunctionException::class)
    fun p(index: Int): Function {
        return Projection(index)
    }

    fun s(): Function {
        return Successor()
    }

    //(x,y) -> x + y
    fun addition(): Function {
        val first = p(1)
        return Recursion(
            first,
            first.andThen(s())
        )
    }

    //x -> x + value
    fun add(value: Long): Function {
        return addition().compose(
            p(1),
            c(value)
        )
    }

    //x -> x - 1
    fun predecessor(): Function {
        return Recursion(
            c(0),
            p(2)
        )
    }

    //(x,y) -> x - y
    fun subtraction(): Function {
        val first = p(1)
        return Recursion(
            first,
            first.andThen(predecessor())
        )
            .compose(
                p(2),
                p(1)
            )
    }

    @Throws(FunctionException::class)
    fun subtract(value: Long): Function {
        return subtraction().compose(
            p(1),
            c(value)
        )
    }

    @Throws(FunctionException::class)
    fun subtractFrom(value: Long): Function {
        return subtraction().compose(
            c(value),
            p(1)
        )
    }

    //(x,y) -> x * y
    fun multiplication(): Function {
        return Recursion(
            c(0),
            addition().compose(
                p(1),
                p(3)
            )
        )
    }

    //x -> x * value
    @Throws(FunctionException::class)
    fun multiplyBy(value: Long): Function {
        return multiplication().compose(
            p(1),
            c(value)
        )
    }

    //x -> x²
    @Throws(FunctionException::class)
    fun square(): Function {
        return multiplication().compose(
            p(1),
            p(1)
        )
    }

    //(x,y) -> x^y
    fun exp(): Function {
        return Recursion(
            c(1),
            multiplication().compose(
                p(1),
                p(3)
            )
        )
            .compose(p(2), p(1))
    }

    fun caseDifferentiation(
        differentiationFunction: Function,
        zeroCaseFunction: Function,
        otherCaseFunction: Function
    ): Function {
        val zeroCaseTestFunction = multiplication().compose(
            zeroCaseFunction,
            differentiationFunction
                .andThen(subtractFrom(1))
        )

        val otherCaseTestFunction = multiplication().compose(
            otherCaseFunction,
            differentiationFunction
                .andThen(subtractFrom(1))
                .andThen(subtractFrom(1))
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
                p(2)
                    .andThen(s()),
                p(1)
            )
        )
    }

    @Throws(FunctionException::class)
    private fun boundedMuOperatorDifferentiationFunction(function: Function): Function {
        //first test function: function(m+1, x1, ..., xk)
        val firstTestArguments = Array(function.requiredArgumentCount) { p(it + 2)}
        firstTestArguments[0] = p(2).andThen(s())
        val firstTestFunction = function.compose(*firstTestArguments)

        //second test function: boundedMuOperator(m, x1, ..., xk)
        val secondTestFunction = p(1)

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
            p(2),
            multiplication().compose(
                p(1),
                p(3)
            )
        )

        return boundedMuOperator(g).compose(
            p(1),
            p(1),
            p(2)
        )
    }

    //(x,y) -> floor(x / y)
    //or 0 if y == 0
    fun floorDivision(): Function {
        val ceilingDivision = ceilingDivision()

        val differentiationFunction = subtraction().compose(
            multiplication().compose(
                ceilingDivision,
                p(2)
            ),
            p(1)
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
                p(2),
                multiplication().compose(
                    p(1),
                    p(3)
                )
            ),
            subtraction().compose(
                multiplication().compose(
                    p(1),
                    p(3)
                ),
                p(2)
            )
        )

        return boundedMuOperator(g).compose(
            p(1),
            p(1),
            p(2)
        )
    }


    //x -> logBase(x); if logBase(x) is a natural number
    //x -> 0; else
    @Throws(FunctionException::class)
    fun log(base: Long): Function {
        val firstTestFunction = subtraction().compose(
            p(2),
            exp().compose(
                c(base),
                p(1)
            )
        )

        val secondTestFunction = subtraction().compose(
            exp().compose(
                c(base),
                p(1)
            ),
            p(2)
        )


        return boundedMuOperator(
            addition().compose(
                firstTestFunction,
                secondTestFunction
            )
        ).compose(
            p(1),
            p(1)
        )
    }
}
