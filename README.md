<p align="center"><img alt="REFUNK" src="doc/logo.png"></p>

<p align="center">
    <a href="https://www.gnu.org/licenses/gpl-3.0.en.html"><img alt="License" src="https://img.shields.io/github/license/DerYeger/refunk?color=40aef6&style=for-the-badge"></a>
    <a href="https://jitpack.io/#eu.yeger/refunk"><img alt="JitPack" src="https://img.shields.io/jitpack/v/github/DerYeger/refunk?color=7963dc&style=for-the-badge"></a>
    <img alt="Contributions" src="https://img.shields.io/badge/contributions-welcome-da4c99?style=for-the-badge">
    <a href="https://codecov.io/gh/DerYeger/refunk"><img alt="Coverage" src="https://img.shields.io/codecov/c/github/deryeger/refunk?color=eaa232&style=for-the-badge"></a>
    <a href="https://travis-ci.com/DerYeger/refunk"><img alt="Build" src="https://img.shields.io/travis/com/deryeger/refunk/master?color=40aef6&style=for-the-badge"></a>
</p>

***REFUNK*** is small and lightweight library for studying and evaluating **primitive recursive functions** in Kotlin.\
It provides a rich set of methods for defining functions in a more **natural** way than other functional frameworks and libraries.

An interactive playground is available at https://refunk.janmueller.dev.

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
  implementation 'eu.yeger:refunk:{version}'
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
  &lt;groupId&gt;eu.yeger&lt;/groupId&gt;
    &lt;artifactId&gt;refunk&lt;/artifactId&gt;
  &lt;version&gt;{version}&lt;/version&gt;
&lt;/dependency&gt;
</pre>
</details>

## Usage

### Basic functions

- `val c = constant(value)` with macros `const(value)`, `zero` and `one`.
- `val s = Successor()` with macro `successor`.
- `val p = projection(index)` with macros `first` to `tenth`.

### Composition

Function composition is handled by the `Composition` class and various wrapper methods.
```
val f = ... 
val g1 = ... 
...
val gn = ...
val myComposition = f of { g1 and ... and gn }
val myAltComp = f(g1, ..., gn)
```
The example below demonstrates simple composition using the, in this case optional, projections `first` and `second`.
```
// f(x, y) = 2 * (x + y)
val f = multiplication(const(2), addition(first, second)) // `addition(first, second)` is equal to `addition`
val result = f(10, 11) // = 42
```
Unary functions can be composed with `Function::andThen`.
```
val myComposition = myFunction andThen myUnaryFunction
```

### Recursion

Recursions can be defined using multiple extension methods.
```
val myRecursion = recursive(myRecursiveCaseFunction) withBaseCase myBaseCaseFunction                
...             = recursive { 
                        aFunction of anotherFunction 
                  } withBaseCase { 
                        someFunction andThen someOtherFunction 
                  }
```
Named projections help using the recursion results, parameters and arguments as well.
```          
val addition = recursive { successor of recursionResult } withBaseCase firstBaseCaseArgument
val predecessor = recursive { recursionParameter } withBaseCase zero
```

### Invocation

The operator `Function::invoke` evaluates the function for the given arguments.
```
val addTwo = successor andThen successor
println(addTwo(0)) //prints 2
println(addTwo(40)) //prints 42

val myFunction = predecessor of addition
println(myFunction(3, 40)) //prints 42
```

### Additional information

More examples and various macros can be found [here](src/main/kotlin/eu/yeger/refunk/recursive/RecursiveFunctions.kt).

## Non-recursive implementations

***REFUNK*** also includes non-recursive [implementations](src/main/kotlin/eu/yeger/refunk/non_recursive/NonRecursiveFunctions.kt) of commonly used functions.\
They are **interchangeable** with the recursive implementations and provide **improved performance** (and less StackOverflowErrors).\
Using the non-recursive implementations of macros is highly **recommended**.

## Exceptions and error handling

- Invoking a function will throw an `ArityException` for invalid amounts of arguments.
- Projecting a negative index will throw a `ProjectionException`.
- Composing functions will throw a `CompositionException` if the arity of the evaluating function and the number of provided functions do not match.
- Invoking functions or creating constants with negative values will throw a `NaturalNumberException`.
- Any provided method will throw an `OverflowException` if an overflow occurs during evaluation.

## Disclaimer

This library is intended as a tool for studying primitive recursive functions.\
Because the implementation is based on experimental Kotlin features, using them in production is not recommended.
