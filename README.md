# Primitive Recursive Functions

> Primitive recursive functions made simple with Kotlin

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![Release](https://jitpack.io/v/DerYeger/primitive-recursive-functions.svg)](https://jitpack.io/#DerYeger/primitive-recursive-functions)

A small and lightweight library for studying and evaluating primitive recursive functions in Kotlin.

## Installation

### Gradle

```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
```
dependencies {
  implementation 'com.github.DerYeger:primitive-recursive-functions:v1.0.1'
}
```

### Maven

```
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```
```
<dependency>
  <groupId>com.github.DerYeger</groupId>
    <artifactId>primitive-recursive-functions</artifactId>
  <version>v1.0.1</version>
</dependency>
```

## Usage

### Basic functions

- `val c = Constant(value)`
- `val s = Successor()`
- `val p = Projection(index)`
- `val myRecursion = Recursion(myBaseCaseFunction, myRecursiveCaseFunction)`

### Composition

Composed functions like `(args) -> f(g1(args), ..., gn(args))` can be created with the `Composition` class 
```
val f = ... 
val g1 = ... 
...
val gn = ...
val myComposition = Composition(f, g1, ..., gn)
```
or by using `Function::compose`.
```
val myComposition = f.compose(g1, ..., gn)
```
Unary functions can also be composed by using `Function::andThen`
```
val myComposition = myFunction.andThen(myUnaryFunction)
```

### Evaluation

`Function::apply` returns the result for the given arguments.
```
val plusTwo = Successor().andThen(Successor())
println(plusTwo.apply(0)) //prints 2
println(plusTwo.apply(40)) //prints 42
```
Projection and Successor are evaluated lazily, meaning only the required argument is evaluated.\
By default, Composition and and Recursion are not lazy. However, both can be set to evaluate lazily as well.
```
val myLazyComposition = Composition(myEvaluator, myFunctions, lazy = true)
...                   = myEvaluator.compose(myFunctions, lazy = true)
...                   = myFunction.andThen(myUnaryFunction, lazy = true)
val myLazyRecursion = Recursion(myBaseCaseFunction, myRecursiveCaseFunction, lazy = true)
```
Additional examples and various macros can be found [here](src/main/kotlin/eu/yeger/prf/Functions.kt).\
Non-recursive [implementations](src/main/kotlin/eu/yeger/prf/non_recursive/NonRecursiveFunctions.kt) of all macros are included as well.\
They are interchangeable with the recursive implementations and provide improved performance (and less StackOverflowErrors).

## Exceptions

- Evaluating a function will throw an `ArityException` if not enough arguments were passed.
- Setting the arity of a function to a negative value will throw an `ArityException`.
- Projecting a negative index will throw a `ProjectionException`.
- Composing functions will throw a `CompositionException` if the arity of the evaluating function is not met.
- Composing with `andThen` will throw a `CompositionException` if the parameter is not an unary function.
- Negative values will, at any point during the evaluation and instantiation, throw a `NaturalNumberException`.
- Evaluating a `Successor` function will throw a `NaturalNumberException` if it would cause an overflow. **Note**: None of the non-recursive macros will throw an exception in this case, and instead set the value to 0.

## Intention

This library is intended as a tool for studying primitive recursive functions, since evaluating them by hand can be quite tedious.\
Because the implementation is based on experimental Kotlin features, using them in production is not recommended.
