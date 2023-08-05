// SPDX-License-Identifier: MIT
package dev.andrybak.util.function.kotlin

/**
 * Returns a list of `A`s from [`Left`][Either.Left] values of this [`Iterable<Either<A, B>>`][Iterable].
 *
 * @param A type for [Either.Left]
 * @param B type for [Either.Right]
 * @return a [`List<A>`][List] from [`Left`][Either.Left] values of this [`Iterable<Either<A, B>>`][Iterable]
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
 * @return a [`List<B>`][List] from [`Right`][Either.Right] values of this [`Iterable<Either<A, B>>`][Iterable]
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
