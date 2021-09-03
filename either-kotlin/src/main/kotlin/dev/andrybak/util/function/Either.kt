/*
 * Copyright (C) 2021 Andrei Rybak
 *
 * This file is part of Either-JVM library.
 *
 * Either-JVM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Either-JVM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Either-JVM.  If not, see <https://www.gnu.org/licenses/>.
 */
package dev.andrybak.util.function

/**
 * Implementation of functional programming abstraction `Either` for Kotlin.
 *
 * Inspired by [Haskell's data type `Either`](https://hackage.haskell.org/package/base/docs/Prelude.html#t:Either).
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
 * Second implementation of the function [either] to allow partial application.
 * Analogue of [Haskell's function `either`](https://hackage.haskell.org/package/base/docs/Prelude.html#v:either).
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
