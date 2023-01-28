// SPDX-License-Identifier: MIT
package dev.andrybak.util.function

/**
 * Implementation of functional programming abstraction `Either` for Kotlin.
 *
 * Inspired by [Haskell's data type `Either`](https://hackage.haskell.org/package/base/docs/Data-Either.html).
 *
 * @param A type for [Left]
 * @param B type for [Right]
 */
sealed class Either<A, B> {
	data class Left<A, B>(val leftValue: A) : Either<A, B>()
	data class Right<A, B>(val rightValue: B) : Either<A, B>()

	companion object {
		/**
		 * Create a [Left] with given value of type `A`.
		 */
		fun <A, B> left(a: A): Either<A, B> = Left(a)

		/**
		 * Create a [Right] with given value of type `B`.
		 */
		fun <A, B> right(b: B): Either<A, B> = Right(b)
	}
}

/**
 * Apply one of given functions to given [Either] value, depending on its type.
 *
 * Case analysis for the [Either] type. If the value is [Either.Left], apply the first function to the
 * value; if it is [Either.Right], apply the second function.
 *
 * @param f function to apply to [Either.Left]
 * @param g function to apply to [Either.Right]
 * @param A type for [Either.Left]
 * @param B type for [Either.Right]
 * @param C return type of functions
 * @return result of applying one of the given functions to given [Either] value.
 */
fun <A, B, C> either(f: (A) -> C, g: (B) -> C, e: Either<A, B>): C {
	return when (e) {
		is Either.Left -> f(e.leftValue)
		is Either.Right -> g(e.rightValue)
	}
}

/**
 * Convert two functions, one which takes `A` and returns `C` and another which takes `B` and
 * returns `C`, into a function that takes an `Either` and returns `C`.
 *
 * Second implementation of the function [either] to allow partial application.
 *
 * Implementation of [Haskell function `either`](https://hackage.haskell.org/package/base/docs/Data-Either.html#v:either)
 * in Kotlin.
 *
 * @param f function to apply to [Either.Left]
 * @param g function to apply to [Either.Right]
 * @param A type for [Either.Left]
 * @param B type for [Either.Right]
 * @param C return type of functions
 * @return function which takes an [Either] and returns result of applying the function corresponding to its type.
 */
fun <A, B, C> either(f: (A) -> C, g: (B) -> C): (Either<A, B>) -> C {
	return { e ->
		when (e) {
			is Either.Left -> f(e.leftValue)
			is Either.Right -> g(e.rightValue)
		}
	}
}
