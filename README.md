# Primitive Recursive Functions

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![Release](https://jitpack.io/v/DerYeger/primitive-recursive-functions.svg)](https://jitpack.io/#DerYeger/primitive-recursive-functions)

A small and lightweight library for creating primitive recursive functions in Kotlin.

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

```
val f = multiplyBy(3).andThen(add(6))
val firstResult = f.apply(4) //18
val secondResult = f.apply(1) //9
```

Additional examples can be found [here](src/test/kotlin/eu/yeger/prf/ComplexFunctionTests.kt).


## Disclaimer

This library is intended as a tool for studying primitive recursive functions, since evaluating them by hand can be quite tedious.\
Due to the nature of primitive **recursive** functions, the performance is not great and using them in production code is not recommended. 
