// SPDX-License-Identifier: MIT
package dev.andrybak.util.function.samples;

import dev.andrybak.util.function.java8.Either;

import java.util.function.Function;

/**
 * Commonly used functions for {@link Either}.
 * This abstraction happens to be called "monad", don't worry about it.
 * <p>
 * {@link Either Either&lt;A, B&gt;} is a monad <em>in</em> its second type parameter. That's why
 * function {@link #bind} changes the types from {@code <A, B>} to {@code <A, C>}.
 * An object of type {@code B} is stored inside of the {@link Either} monad.
 * Objects of type {@code A} just happen to be there.
 * </p>
 */
public class EitherMonad {
	private EitherMonad() {
		throw new AssertionError();
	}

	/**
	 * Puts given object of type {@code B} into the {@link Either} monad.
	 * Compare to {@link java.util.Optional#of}.
	 */
	public static <A, B> Either<A, B> pure(B b) {
		return Either.right(b);
	}

	/**
	 * @see #bind(Either, Function)
	 */
	public static <A, B, C> Function<Function<B, Either<A, C>>, Either<A, C>> bind(Either<A, B> e) {
		return f -> e.match(Either::left, f);
	}

	/**
	 * Applies given {@link Function} to the {@link Either} monad.
	 * Compare to {@link java.util.Optional#flatMap}.
	 *
	 * @param e   an {@link Either}
	 * @param f   a {@link Function} from type {@code B} to a type {@code C} in the {@link Either} monad
	 * @param <A> type for the {@link dev.andrybak.util.function.java8.Either.Left Left}
	 * @param <B> type for the {@link dev.andrybak.util.function.java8.Either.Right Right} before applying the function
	 * @param <C> type for the {@link dev.andrybak.util.function.java8.Either.Right Right} after applying the function
	 * @return result of applying given {@link Function} to the given {@link Either}
	 */
	public static <A, B, C> Either<A, C> bind(Either<A, B> e, Function<B, Either<A, C>> f) {
		return e.match(Either::left, f);
	}
}
