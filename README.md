# Either-JVM

This is a collection of several libraries for JVM, implemented in several JVM
languages.  All variants of same library implement the `Either` data type in
form of a JVM class.

## Motivation

Experience of people using functional programming, with Haskell in particular,
has shown that [Either](https://hackage.haskell.org/package/base/docs/Data-Either.html)
is a very useful data type.  There are several implementations of it for JVM and
for Kotlin Multiplatform:

- <https://www.javadoc.io/doc/io.vavr/vavr/latest/io/vavr/control/Either.html>
- <https://github.com/functionaljava/functionaljava/blob/series/5.x/core/src/main/java/fj/data/Either.java>
- <https://github.com/meoyawn/kotlin-either>
- <https://github.com/aSoft-Ltd/either>
- <https://github.com/JavierSegoviaCordoba/network-either-kmp>

However, each of them has some combination of the following properties, which
make their usage inconvenient or cumbersome:

- don't include convenient structural pattern matching facilities for their
  `Either` type
- don't use functional interfaces from `java.util.function` directly (actual
  only for Java libraries)
- are included as part of very big unwieldy functional programming library
- don't provide APIs with conventional naming structure
- don't utilize wildcard type parameters, making it hard to use functional
  variables and method references with inexact type
- are not abstract enough, with specialization for something specific, like
  passing an `Either` over the network

Either-JVM is also a personal exercise in library writing and programming
language learning.

## Features

1. Convenient structural pattern matching.
2. For Java libraries – interoperation with `java.util.function.*` and
   `java.util.Stream` APIs.

## `either-java8`

Compatible with Java 8 and any later versions.

## `either-java17`

Compatible with Java 17 and any later versions.  Utilizes the sealed classes
feature which was introduced to Java in [JEP
409](https://openjdk.java.net/jeps/409).

Technically, it can be used together with pattern matching in `switch`
statements and expressions, which was introduced as a preview feature in [JEP
406](https://openjdk.java.net/jeps/406), but I don't recommend it – pattern
matching in `switch` has changed a lot.  Latest preview in Java 20 is described
in [JEP 433](https://openjdk.org/jeps/433).

## `either-kotlin`

_TBD_

_I haven't figured out yet how versioning of Kotlin language works yet, I'll add
something here when I do._

## Usage

_Publishing to a public Maven repository hasn't been set up yet._
