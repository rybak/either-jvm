# Either-JVM

This is a collection of several libraries for JVM, implemented in several JVM
languages.  All variants of same library implement the `Either` data type in
form of a JVM class.

## Motivation

Experience of the functional programming experience, Haskell in particular, has
shown that [Either](https://hackage.haskell.org/package/base/docs/Data-Either.html)
is a very useful data type.  There are several implementations of it for JVM:

- https://www.javadoc.io/doc/io.vavr/vavr/latest/io/vavr/control/Either.html
- https://github.com/functionaljava/functionaljava/blob/series/5.x/core/src/main/java/fj/data/Either.java
- https://github.com/meoyawn/kotlin-either

However, they have various properties, which make their usage inconvenient or
cumbersome:

- don't include convenient structural pattern matching facilities for their
  `Either` type
- don't use Java 8 functional interfaces directly
- are included as part of very big unwieldy functional programming library
- don't provide APIs with conventional naming structure

Either-JVM is also a personal exercise in library writing and programming
language learning.

## Features

1. Convenient structural pattern matching.
2. For Java libraries – interoperation with `java.util.function` and
   `java.util.Stream` APIs.
3. Implementations of functional programming abstractions: Functor, Applicative,
   and Monad for type `Either`.

## `either-java8`

Compatible with Java 8.

## `either-java17`

Compatible with Java 17. Utilizes sealed classes feature which was introduced to
Java in [JEP 409](https://openjdk.java.net/jeps/409). Can be used together with
pattern matching in `switch` statements and expressions, which was introduced as
a preview feature in [JEP 406](https://openjdk.java.net/jeps/406).

## `either-kotlin`

_TBD_
