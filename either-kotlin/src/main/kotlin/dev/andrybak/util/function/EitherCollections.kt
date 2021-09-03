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
 * Returns a list of `A`s from [`Left`][Either.Left] values of this [`Iterable<Either<A, B>>`][Iterable].
 *
 * @param A type for [Either.Left]
 * @param B type for [Either.Right]
 */
fun <A, B> Iterable<Either<A, B>>.lefts(): List<A> {
	return this
		.filter(either({ true }, { false }))
		.map(either(
			{ a -> a },
			{
				throw IllegalStateException("Got a right value after filtering")
			}
		))
}

/**
 * Returns a list of `B`s from [`Right`][Either.Right] values of this [`Iterable<Either<A, B>>`][Iterable].
 *
 * @param A type for [Either.Left]
 * @param B type for [Either.Right]
 */
fun <A, B> Iterable<Either<A, B>>.rights(): List<B> {
	return this
		.filter(either({ false }, { true }))
		.map(either(
			{
				throw IllegalStateException("Got a left value after filtering")
			},
			{ b -> b }
		))
}
