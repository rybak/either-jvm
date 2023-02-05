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
sealed class Either<out A, out B> {
	/**
	 * Pattern matches on this [Either] and returns result of applying the corresponding function.
	 *
	 * @param f function to apply to a value of [Left]
	 * @param g function to apply to a value of [Right]
	 * @param R return type of functions
	 */
	abstract fun <R> match(f: (A) -> R, g: (B) -> R): R

	/**
	 * If this [Either] is [Left], performs the first given action with its value.
	 * If this [Either] is [Right], performs the second given action with its value.
	 *
	 * @param f consumer to apply to a value of [Left]
	 * @param g consumer to apply to a value of [Right]
	 */
	inline fun accept(f: (A) -> Unit, g: (B) -> Unit) {
		when (this) {
			is Left -> f(leftValue)
			is Right -> g(rightValue)
		}
	}

	/**
	 * If this [Either] is [Left], performs the first given action with its value and returns this [Either].
	 * If this [Either] is [Right], performs the second given action with its value and returns this [Either].
	 *
	 * @param f consumer to apply to a value of [Left]
	 * @param g consumer to apply to a value of [Right]
	 */
	inline fun peek(f: (A) -> Unit, g: (B) -> Unit): Either<A, B> {
		when (this) {
			is Left -> f(leftValue)
			is Right -> g(rightValue)
		}
		return this
	}

	data class Left<A, B>(val leftValue: A) : Either<A, B>() {
		override fun <R> match(f: (A) -> R, g: (B) -> R): R = f(leftValue)
	}

	data class Right<A, B>(val rightValue: B) : Either<A, B>() {
		override fun <R> match(f: (A) -> R, g: (B) -> R): R = g(rightValue)
	}

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
 * If given [Either] is [Left][Either.Left], returns result of applying the first given function to its value,
 * if it is [Right][Either.Right], returns result of applying the second given function to its value.
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
 * Converts two functions, one which takes `A` and returns `C` and another which takes `B` and
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
