package eu.yeger.prf

fun c(value: Long) = Constant(value)

fun p(index: Int) = Projection(index)

fun s(): Successor = Successor()

//(x,y) -> x + y
fun addition(): Function {
    val first = p(0)
    return Recursion(
        first,
        first andThen s()
    )
}

fun additionOf(collector: () -> Array<Function>) = addition().of(collector)

//x -> x + value
fun add(value: Long) = additionOf { p(0) and c(value) }

//x -> x - 1
fun predecessor() = Recursion(c(0), p(1))

//(x,y) -> x - y
fun subtraction(): Function {
    val first = p(0)

    return Recursion(first, first andThen predecessor()) of { p(1) and first }
}

fun subtractionOf(collector: () -> Array<Function>) = subtraction().of(collector)

//x -> x - value
fun subtract(value: Long) = subtractionOf{ p(0) and c(value) }

//x -> value - x
fun subtractFrom(value: Long) = subtractionOf { c(value) and p(0) }

fun not() = subtractFrom(1)

//(x,y) -> x * y
fun multiplication() = Recursion(c(0), additionOf { p(0) and p(2) })

fun multiplicationOf(collector: () -> Array<Function>) = multiplication().of(collector)

//x -> x * value
fun multiplyBy(value: Long) =  multiplicationOf{ p(0) and c(value) }

//x -> x²
fun square() =  multiplicationOf { p(0) and p(0) }

//(x,y) -> x^y
fun exp() =
    Recursion(
        c(1),
        multiplicationOf { p(0) and p(2) }
    ) of { p(1) and p(0) }

fun expOf(collector: () -> Array<Function>) = exp().of(collector)

fun caseDifferentiation(
    differentiationFunction: Function,
    zeroCaseFunction: Function,
    otherCaseFunction: Function
): Function {
    val zeroCaseTestFunction = multiplicationOf {
        zeroCaseFunction and (differentiationFunction andThen not())
    }

    val otherCaseTestFunction = multiplicationOf {
        otherCaseFunction and (differentiationFunction andThen not() andThen not())
    }

    return additionOf { zeroCaseTestFunction and otherCaseTestFunction }
}

fun boundedMuOperator(function: Function) =
    Recursion(
        c(0),
        caseDifferentiation(
            boundedMuOperatorDifferentiationFunction(function),
            p(1) andThen s(),
            p(0)
        )
    )

fun boundedMuOperatorOf(function: Function, collector: () -> Array<Function>) = boundedMuOperator(function).of(collector)

internal fun boundedMuOperatorDifferentiationFunction(function: Function): Function {
    val firstTestArguments = Array<Function>(function.arity) { p(it + 1)}
    firstTestArguments[0] = p(1) andThen s()
    val firstTestFunction = function of { firstTestArguments }

    val secondTestFunction = p(0)

    val thirdTestArguments = firstTestArguments.clone()
    thirdTestArguments[0] = c(0)
    val thirdTestFunction = function of { thirdTestArguments }

    return additionOf {
        firstTestFunction and additionOf { secondTestFunction and subtractionOf { c(1) and thirdTestFunction } }
    }
}

//(x,y) -> ceiling(x / y)
fun ceilingDivision(): Function {
    //(n,x,y) -> x - n * y
    val g = subtractionOf {
        p(1) and (multiplicationOf { p(0) and p(2) })
    }

    return boundedMuOperatorOf(g) { p(0) and p(0) and p(1) }
}

//(x,y) -> floor(x / y)
//or 0 if y == 0
fun floorDivision(): Function {
    val ceilingDivision = ceilingDivision()

    val differentiationFunction = subtractionOf {
        multiplicationOf { ceilingDivision and p(1) } and p(0)
    }

    return caseDifferentiation(
        differentiationFunction,
        ceilingDivision,
        ceilingDivision andThen predecessor()
    )
}

//(x,y) -> x / y; if x / y is a natural number
//(x,y) -> 0; else
fun division(): Function {
    //(n,x,y) -> (x - n * y) + (n * y - x)
    val g = additionOf {
        subtractionOf {
            p(1) and multiplicationOf { p(0) and p(2) }
        } and subtractionOf {
            multiplication() of { p(0) and p(2) } and p(1)
        }
    }

    return boundedMuOperatorOf(g) { p(0) and p(0) and p(1) }
}

//WARNING Due to the nature of recursive functions using log will likely result in a StackOverflowError
//x -> logBase(x); if logBase(x) is a natural number
//x -> 0; else
fun log(base: Long): Function {
    val firstTestFunction = subtractionOf { p(1) and (expOf { c(base) and p(0) }) }

    val secondTestFunction = subtractionOf { expOf { c(base) and p(0) } and p(1) }

    val testFunction = additionOf { firstTestFunction and secondTestFunction }

    return boundedMuOperatorOf(testFunction) { p(0) and p(0) }
}
