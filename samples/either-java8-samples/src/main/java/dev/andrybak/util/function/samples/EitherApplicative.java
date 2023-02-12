// SPDX-License-Identifier: MIT
package dev.andrybak.util.function.samples;

import dev.andrybak.util.function.java8.Either;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Commonly used functions for {@link Either}.
 * This abstraction happens to be called "applicative", don't worry about it.
 * <p>
 * {@link Either Either&lt;A, B&gt;} is a functor in its <em>second</em> type parameter. That's why
 * function {@link #apply} changes the types from {@code <E, A>} to {@code <E, B>}.
 * An object of type {@code A} or {@code B} is stored inside of the {@link Either} functor.
 * Objects of type {@code E} just happen to be there.
 * </p>
 * <p>
 * Note: the names of type parameters are deliberately changed to move the focus from objects of type {@link Either}
 * to objects of types {@link Function} and {@link BiFunction}.
 * </p>
 */
public class EitherApplicative {
	private EitherApplicative() {
		throw new AssertionError();
	}

	/**
	 * Puts given object into the {@link Either} applicative.
	 */
	public static <E, A> Either<E, A> pure(A a) {
		return Either.right(a);
	}

	/**
	 * @see #apply(Either, Either)
	 */
	public static <E, A, B> Function<Either<E, A>, Either<E, B>> apply(Either<E, Function<A, B>> ef) {
		return ea -> apply(ef, ea);
	}

	/**
	 *
	 */
	public static <E, A, B> Either<E, B> apply(Either<E, Function<A, B>> ef, Either<E, A> ea) {
		return ef.match(
				Either::left,
				f -> EitherFunctor.fmap(f, ea)
		);
	}

	/**
	 * liftA2 :: (a -> b -> c) -> f a -> f b -> f c
	 */
	public static <E, A, B, C> Either<E, C> lift2(BiFunction<A, B, C> f, Either<E, A> ea, Either<E, B> eb) {
		Either<E, Function<B, C>> eg = EitherFunctor.fmap(f, ea);
		return apply(eg, eb);
	}
}
