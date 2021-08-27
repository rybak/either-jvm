## To Do

- Maybe move static functions into a separate class(es)
    * [ ] EitherTypeClasses?
    * [ ] can't think of class name for functions `either` -- they are just convenience functions, with slightly different type than match
- equals & hashCode & Serializeable
- Javadocs for static factory methods `left` and `right`
- Foldable impl
    > https://hackage.haskell.org/package/base-4.15.0.0/docs/src/Data-Foldable.html#Foldable
- Add Haskell type notation to Javadocs
- Add either-java16 or whatever version with sealed classes (possibly experimental)
    * [ ] https://openjdk.java.net/jeps/406 -- pattern matching is preview feature in Java 17
    * [ ] Wait until JDK with needed flag is available in Ubuntu https://stackoverflow.com/q/68702414/1083697

## Doing

- Kotlin tests
- Kotlin: data class?
    * [ ] Are data classes a good idea for Left and Right? Left#a and Right#b seemingly make it too attached to <A, B> type parameter naming

## Done

- Move src to either-java8/ subproject
    * [x] Gradle: add JVM version to the build script
- Add README.md
- Move java8 and java17 impls into separate packages to disambig
    > Seem to have hit an IntelliJ bug -- IDEA wants to add more classes to `permits` of Either in java17 version
- Kotlin impl
    * [x] learn about Kotlin Generics
