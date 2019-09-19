<p align="center"><img src="doc/logo_transparent.png"></p>

<p align="center">
    <img alt="GitHub" src="https://img.shields.io/github/license/DerYeger/refunk?color=40aef6&style=for-the-badge">
    <img alt="JitPack" src="https://img.shields.io/jitpack/v/github/DerYeger/refunk?color=7963dc&style=for-the-badge">
    <img alt="Contributions welcome" src="https://img.shields.io/badge/contributions-welcome-da4c99?style=for-the-badge">
    <img alt="JitPack - Downloads" src="https://img.shields.io/jitpack/dm/github/DerYeger/refunk?color=eaa232&style=for-the-badge">
</p>

***REFUNK*** is small and lightweight library for studying and evaluating **primitive recursive functions** in Kotlin.\
It provides a rich set of methods for defining functions in a more **natural** way than other functional frameworks and libraries.

## Installation

### Gradle

<details>
<summary>Show instructions</summary>
<pre>
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
</pre>
<pre>
dependencies {
  implementation 'com.github.DerYeger:refunk:v2.0.1'
}
</pre>
</details>


### Maven

<details>
<summary>Show instructions</summary>
<pre>
&lt;repositories&gt;
  &lt;repository&gt;
    &lt;id&gt;jitpack.io&lt;/id&gt;
    &lt;url&gt;https://jitpack.io&lt;/url&gt;
  &lt;/repository&gt;
&lt;/repositories&gt;
</pre>
<pre>
&lt;dependency&gt;
  &lt;groupId&gt;com.github.DerYeger&lt;/groupId&gt;
    &lt;artifactId&gt;refunk&lt;/artifactId&gt;
  &lt;version&gt;v2.0.1&lt;/version&gt;
&lt;/dependency&gt;
</pre>
</details>

## Usage

### Basic functions

- `val c = Constant(value)` with macros `c(value)`, `zero()` and `one()`.
- `val s = Successor()`
- `val p = Projection(index)` with macros `p(index)`, `first()`, `second()` and `third()`.

### Composition

Composed functions like `(args) -> f(g1(args), ..., gn(args))` can be created with the `Composition` class and various extension methods.
```
val f = ... 
val g1 = ... 
...
val gn = ...
val myComposition = f of { g1 and ... and gn }
```
Unary functions can be composed with `Function::andThen`
```
val myComposition = myFunction andThen myUnaryFunction
```

### Recursion

Recursions can be created using multiple extension methods.
```
val myRecursion = recursive(myRecursiveCaseFunction) withBaseCase myBaseCaseFunction                
...             = recursive { 
                        aFunction of anotherFunction 
                  } withBaseCase { 
                        someFunction andThen someOtherFunction 
                  }
```

### Evaluation

`Function::apply` evaluates the function for the given arguments.
```
val plusTwo = Successor() andThen Successor()
println(plusTwo.apply(0)) //prints 2
println(plusTwo.apply(40)) //prints 42
```

### Additional information

More examples and various macros can be found [here](src/main/kotlin/eu/yeger/refunk/recursive/RecursiveFunctions.kt).

## Non-recursive implementations

***REFUNK*** also includes non-recursive [implementations](src/main/kotlin/eu/yeger/refunk/non_recursive/NonRecursiveFunctions.kt) of commonly used functions.\
They are **interchangeable** with the recursive implementations and provide **improved performance** (and less StackOverflowErrors).\
Using the non-recursive implementations of macros is highly **recommended**.

## Exceptions and error handling

- Evaluating a function will throw an `ArityException` if not enough arguments were passed.
- Setting the arity of a function to a negative value will throw an `ArityException`.
- Projecting a negative index will throw a `ProjectionException`.
- Composing functions will throw a `CompositionException` if the arity of the evaluating function and the number of provided functions do not match.
- Applying or creating constants with negative values will throw a `NaturalNumberException`.
- Any provided method will throw an `OverflowException` if an overflow occurs during evaluation.

## Disclaimer

This library is intended as a tool for studying primitive recursive functions.\
Because the implementation is based on experimental Kotlin features, using them in production is not recommended.
