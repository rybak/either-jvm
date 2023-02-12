// SPDX-License-Identifier: MIT
package dev.andrybak.util.function.samples;

import dev.andrybak.util.function.java8.Either;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Commonly used functions for {@link Either}.
 * This abstraction happens to be called "functor", don't worry about it.
 * <p>
 * {@link Either Either&lt;E, A&gt;} is a functor <em>in</em> its second type parameter.
 * That's why functions {@code fmap} change the types from {@code <E, A>} to {@code <E, B>}.
 * An object of type {@code A} or {@code B} is stored inside of the {@link Either} functor.
 * Objects of type {@code E} just happen to be there.
 * </p>
 */
public class EitherFunctor {
	private EitherFunctor() {
		throw new AssertionError();
	}

	/**
	 * Converts given function from type {@code B} to type {@code C} to a function that converts
	 * an {@link Either Either&lt;A, B&gt;} into an {@link Either Either&lt;A, C&gt;},
	 * i.e. it applies the given function to the {@link Either} functor.
	 *
	 * @see #fmap(Function, Either)
	 */
	public static <E, A, B> Function<Either<E, A>, Either<E, B>> fmap(Function<A, B> f) {
		return e -> fmap(f, e);
	}

	/**
	 * Applies given {@link Function} to the {@link Either} functor.
	 * Compare to {@link java.util.Optional#map}.
	 *
	 * @param f   function to apply to the given {@link Either}
	 * @param e   {@link Either} to apply the given function to
	 * @param <E> type for the {@link dev.andrybak.util.function.java8.Either.Left Left}
	 * @param <A> type for the {@link dev.andrybak.util.function.java8.Either.Right Right} before applying the function
	 * @param <B> type for the {@link dev.andrybak.util.function.java8.Either.Right Right} after applying the function
	 * @return result of applying given {@link Function} to the given {@link Either}
	 */
	public static <E, A, B> Either<E, B> fmap(Function<A, B> f, Either<E, A> e) {
		return e.match(
				Either::left,
				(A a) -> Either.right(f.apply(a))
		);
	}

	/**
	 * Java doesn't have partial application, so a special case is needed for {@link BiFunction}.
	 */
	public static <E, A, B, C> Either<E, Function<B, C>> fmap(BiFunction<A, B, C> f, Either<E, A> ea) {
		return fmap(a -> b -> f.apply(a, b), ea);
	}
}
