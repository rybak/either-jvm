## To Do

- Foldable impl
    > https://hackage.haskell.org/package/base-4.15.0.0/docs/src/Data-Foldable.html#Foldable
- add code samples
    * [ ] code samples in README
    * [ ] code samples as separate subproject
- Use switch-pattern-matching in either-java17 (preview feature)
    * [ ] Wait until Java 17 is released
    * [ ] Wait until JDK with needed flag is available in Ubuntu https://stackoverflow.com/q/68702414/1083697
    * [ ] https://openjdk.java.net/jeps/406 -- pattern matching is preview feature in Java 17
- Kotlin: data class?
    * [ ] Are data classes a good idea for Left and Right? Left#a and Right#b seemingly make it too attached to <A, B> type parameter naming
    * [ ] are data classes with all fields private  possible?
    * [ ] data class Left(private val a: A) ???
- Add Haskell type notation to Javadocs
- either-java8 JSON
    > Since we'll make classes Serialiazeable, there should be a JSON serialization implementation for popular JSON libraries.
    * [ ] either-java8-gson -- not possible, because of type erasure -- but we could help users make their own Json(De)Serializers, maybe
    * [ ] either-java8-jackson
    * [ ] ???
- kotlin-mpp
    * [ ] https://kotlinlang.org/docs/mpp-create-lib.html
    * [ ] https://kotlinlang.org/docs/multiplatform-library.html
- Kotlin testing frameworks
    * [ ] look into https://kotlinlang.org/api/latest/kotlin.test/
    * [ ] look into https://kotest.io/
- jar manifest
    > Figure out adding information to the manifest files of the published jars. Especially the implementation version field.

## Doing

- Check generics
    > Does something like Either need PECS applied to generics in method `match`, for example? One might consider Either as a collection of elements of type A or B.
    * [x] see branch `generics` on GitHub/GitLab
    * [ ] Kotlin
- equals & hashCode & Serializable
    * [x] Serializable
    * [x] Tests for serialization
    * [ ] equals & hashCode
    * [ ] tests for equals & hashCode

## Done

- Move src to either-java8/ subproject
    * [x] Gradle: add JVM version to the build script
- Add README.md
- Move java8 and java17 impls into separate packages to disambig
    > Seem to have hit an IntelliJ bug -- IDEA wants to add more classes to `permits` of Either in java17 version
- Kotlin impl
    * [x] learn about Kotlin Generics
- Add either-java16 or whatever version with sealed classes (possibly experimental)
- Maybe move static functions into a separate class(es)
    * [x] EitherTypeClasses?
    * [x] can't think of class name for functions `either` -- they are just convenience functions, with slightly different type than match
- Kotlin tests
- Javadocs for static factory methods `left` and `right`
- pre-push hook
    > https://stackoverflow.com/a/61621713/1083697
- Gradle: task configuration avoidance
    > compare how task `test` is configured in kotlin and java versions
    * [x] stop using plain `test { ... }` block
- Gradle update/cleanup 2021-09
    * [x] upgrade junit version
    * [x] clean up build script for Kotlin plugin
- Upgrade to Gradle 7.6
- Upgrade Kotlin Gradle plugin
- MIT License
    > I've decided that either-jvm isn't worth keeping under LGPL
    * [x] figure out SPDX ID stuff
    * [x] delete all copyright comments in code
    * [x] replace COPYING and LICENSE with MIT license
- Gradle: specify --release for javac
    > Implemented in commit af9bdcf (either-java8: clean up Gradle build script, 2021-08-07)
- Java 17 EitherStreams
    > Pretty much a copy of -java8 version. The only difference I've noticed is List.of and Stream.toList usage possibility in the tests.
    * [x] WIP in local branch
- Set up publishing
    > Use Gradle plugin maven-publish for publishing of jars to Maven repositories
