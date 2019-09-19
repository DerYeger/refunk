# Refunk

> Primitive recursive functions made simple with Kotlin

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![Release](https://jitpack.io/v/DerYeger/refunk.svg)](https://jitpack.io/#DerYeger/refunk)

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
  implementation 'com.github.DerYeger:refunk:v1.1.0'
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
    <artifactId>refunk</artifactId>
  <version>v1.1.0</version>
</dependency>
```

## Usage

### Basic functions

- `val c = Constant(value)`
- `val s = Successor()`
- `val p = Projection(index)`
- `val myRecursion = Recursion(myBaseCaseFunction, myRecursiveCaseFunction)`

### Composition

Composed functions like `(args) -> f(g1(args), ..., gn(args))` can be created with the `Composition` class and various other methods
```
val f = ... 
val g1 = ... 
...
val gn = ...
val myExplicitComposition = Composition(f, g1, ..., gn)
val myMethodComposition = f.compose(g1, ..., gn)
val myInfixComposition = f of { g1 and ... and gn }
```
Unary functions can also be composed by using `Function::andThen`
```
val myComposition = myFunction.andThen(myUnaryFunction)
val myInfixComposition = myFunction andThen myUnaryFunction
```

### Evaluation

`Function::apply` returns the result for the given arguments.
```
val plusTwo = Successor() andThen Successor()
println(plusTwo.apply(0)) //prints 2
println(plusTwo.apply(40)) //prints 42
```
Projection, Successor, Recursion and composition with andThen are evaluated lazily, meaning only the required arguments are evaluated.\
In order to avoid StackOverflowErrors, Composition is not lazy. However, compositions (with the exception of infix compositions) can be set to evaluate lazily as well.
```
val myLazyComposition = Composition(myEvaluator, myFunctions, lazy = true)
...                   = myEvaluator.compose(myFunctions, lazy = true)
```
Additional examples and various macros can be found [here](src/main/kotlin/eu/yeger/refunk/recursive/RecursiveFunctions.kt).\
Non-recursive [implementations](src/main/kotlin/eu/yeger/refunk/non_recursive/NonRecursiveFunctions.kt) of all macros are included as well.\
They are interchangeable with the recursive implementations and provide improved performance (and less StackOverflowErrors).\
Using the non-recursive implementations of macros is highly recommended.

## Exceptions

- Evaluating a function will throw an `ArityException` if not enough arguments were passed.
- Setting the arity of a function to a negative value will throw an `ArityException`.
- Projecting a negative index will throw a `ProjectionException`.
- Composing functions will throw a `CompositionException` if the arity of the evaluating function and the number of provided functions do not match.
- Applying or creating constants with negative values will throw a `NaturalNumberException`.
- Any provided method will throw an `OverflowException` if an overflow occurs during evaluation.

## Disclaimer

This library is intended as a tool for studying primitive recursive functions.\
Because the implementation is based on experimental Kotlin features, using them in production is not recommended.
