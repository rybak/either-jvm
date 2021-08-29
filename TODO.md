## To Do

- equals & hashCode & Serializeable
- Foldable impl
    > https://hackage.haskell.org/package/base-4.15.0.0/docs/src/Data-Foldable.html#Foldable
- Add Haskell type notation to Javadocs
- either-java8 JSON
    > Since we'll make classes Serialiazeable, there should be a JSON serialization implementation for popular JSON libraries.
    * [ ] either-java8-gson
    * [ ] either-java8-jackson
    * [ ] ???
- kotlin-mpp
    * [ ] https://kotlinlang.org/docs/mpp-create-lib.html
    * [ ] https://kotlinlang.org/docs/multiplatform-library.html
- Gradle: task configuration avoidance
    > compare how task `test` is configured in kotlin and java versions
    * [ ] stop using plain `test { ... }` block
- Use switch-pattern-matching in either-java17 (preview feature)
    * [ ] Wait until Java 17 is released
    * [ ] Wait until JDK with needed flag is available in Ubuntu https://stackoverflow.com/q/68702414/1083697
    * [ ] https://openjdk.java.net/jeps/406 -- pattern matching is preview feature in Java 17

## Doing

- Kotlin: data class?
    * [ ] Are data classes a good idea for Left and Right? Left#a and Right#b seemingly make it too attached to <A, B> type parameter naming
    * [ ] are data classes with all fields private  possible?

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
