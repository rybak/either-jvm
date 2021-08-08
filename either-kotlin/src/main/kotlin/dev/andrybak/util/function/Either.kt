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

// TODO figure out Kotlin's generics https://kotlinlang.org/docs/generics.html
sealed class Either<A, B> {
	data class Left<A, B>(val a: A) : Either<A, B>()
	data class Right<A, B>(val b: B) : Either<A, B>()
}

/**
 * https://hackage.haskell.org/package/base/docs/Prelude.html#v:either
 */
fun <A, B, C> either(f: (A) -> C, g: (B) -> C, e: Either<A, B>): C {
	return when (e) {
		is Either.Left -> f(e.a)
		is Either.Right -> g(e.b)
	}
}

/**
 * Second implementation of the function {@code either} to allow partial application.
 */
fun <A, B, C> either(f: (A) -> C, g: (B) -> C): (Either<A, B>) -> C {
	return { e ->
		when (e) {
			is Either.Left -> f(e.a)
			is Either.Right -> g(e.b)
		}
	}
}
