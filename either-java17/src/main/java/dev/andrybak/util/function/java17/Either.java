// SPDX-License-Identifier: MIT
package dev.andrybak.util.function.java17;

import java.io.Serializable;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Java implementation of functional programming abstraction {@code Either} for Java 17 using sealed classes feature.
 * <p>
 * The most basic and most important part of API of this class is instance method {@link #match(Function, Function)}.
 * It allows to structurally pattern match on the left and right values stored in objects of type {@code Either}.
 * </p>
 * <p>
 * Note, that for implementations of functions for various Haskell type classes, we use a non-existent notation of
 * partially types for generics. The type of left values ({@code <A>} of the generics of the class) is fixed in these
 * type classes, while type of right values ({@code <B>}) changes. For that purpose, a fixed {@code <E>} type value is
 * used in the documentation, like so: {@code Either<E>}.
 * </p>
 * <p>
 * If both types {@code A} and {@code B} are {@link Serializable} then {@code Either<A, B>} can be serialized.
 * </p>
 * <p>
 * Inspired by
 * <a href="https://hackage.haskell.org/package/base/docs/Data-Either.html">Haskell's type {@code Either}</a>.
 * </p>
 *
 * @param <A> type for {@link Left}
 * @param <B> type for {@link Right}
 */
public sealed abstract class Either<A, B> implements Serializable permits Left, Right {
	/**
	 * @implNote Constructor is not private, because {@link Left} and {@link Right} classes are not nested in *
	 * {@link Either}.
	 */
	Either() {
	}

	/**
	 * Create a {@link Left} with given value of type {@code A}.
	 */
	public static <A, B> Either<A, B> left(A a) {
		return new Left<>(a);
	}

	/**
	 * Create a {@link Right} with given value of type {@code B}.
	 */
	public static <A, B> Either<A, B> right(B b) {
		return new Right<>(b);
	}

	/**
	 * Implementation of the {@code map} function of the
	 * <a href="https://hackage.haskell.org/package/base/docs/Data-Functor.html">Functor</a> abstraction for
	 * {@code Either<E>}.
	 */
	public static <E, A, B> Either<E, B> map(Function<A, B> f, Either<E, A> e) {
		return e.match(
				Left::new,
				b -> new Right<>(f.apply(b))
		);
	}

	/**
	 * @implNote second implementation of function {@code map} is needed because Java doesn't support partial
	 * application of functions.
	 */
	public static <E, A, B> Function<Either<E, A>, Either<E, B>> map(Function<A, B> f) {
		return e -> map(f, e);
	}

	/**
	 * Lift a value into {@code Either<A>}.
	 * <p>
	 * Implementation of the function {@code pure} of the
	 * <a href="https://hackage.haskell.org/package/base/docs/Control-Applicative.html">Applicative</a>
	 * abstraction for {@code Either<E>}.
	 */
	public static <E, A> Either<E, A> pure(A a) {
		return right(a);
	}

	/**
	 * Sequential application. Part of the implementation of the
	 * <a href="https://hackage.haskell.org/package/base/docs/Control-Applicative.html">Applicative</a>
	 * abstraction for {@code Either<E>}.
	 *
	 * @param ef  {@code Either} of function
	 * @param ea  {@code Either} of possible value of type {@code A}
	 * @param <E> type of {@link Left}
	 * @param <A> initial type of {@link Right}
	 * @param <B> type of {@link Right} after applying the function.
	 */
	public static <E, A, B> Either<E, B> sequential(Either<E, Function<A, B>> ef, Either<E, A> ea) {
		return ef.match(
				Left::new,
				f -> map(f, ea)
		);
	}

	/**
	 * @implNote second implementation of function {@code sequential} is needed because Java doesn't support partial
	 * application of functions.
	 */
	public static <E, A, B> Function<Either<E, A>, Either<E, B>> sequential(Either<E, Function<A, B>> ef) {
		return ea -> ef.match(
				Left::new,
				f -> map(f, ea)
		);
	}

	/**
	 * Lift a binary function to {@code Either<E>}. Part of the implementation of the
	 * <a href="https://hackage.haskell.org/package/base/docs/Control-Applicative.html">Applicative</a>
	 * abstraction for {@code Either<E>}.
	 */
	public static <E, A, B, C> Either<E, C> liftA2(BiFunction<A, B, C> f, Either<E, A> ea, Either<E, B> eb) {
		return ea.match(
				Left::new,
				rightA -> eb.match(
						Left::new,
						rightB -> new Right<>(f.apply(rightA, rightB))
				)
		);
	}

	/**
	 * @implNote second implementation of function {@code liftA2} is needed because Java doesn't support partial
	 * application of functions.
	 */
	public static <E, A, B, C> BiFunction<Either<E, A>, Either<E, B>, Either<E, C>> liftA2(BiFunction<A, B, C> f) {
		return (ea, eb) -> ea.match(
				Left::new,
				rightA -> eb.match(
						Left::new,
						rightB -> new Right<>(f.apply(rightA, rightB))
				)
		);
	}

	/**
	 * Implementation of the bind function of the
	 * <a href="https://hackage.haskell.org/package/base/docs/Control-Monad.html">Monad</a>
	 * abstraction for {@code Either<E>}.
	 */
	public static <E, A, B> Either<E, B> bind(Either<E, A> e, Function<A, Either<E, B>> f) {
		return e.match(
				Left::new,
				f
		);
	}

	/**
	 * Convert two functions, one which takes {@code A} and returns {@code C} and another which takes {@code B} and
	 * returns {@code C}, into a {@link Function} that takes an {@code Either<A, B>} and returns {@code C}.
	 * This can be useful for usage with {@link java.util.stream.Stream} API.
	 * <p>
	 * Implementation of
	 * <a href="https://hackage.haskell.org/package/base/docs/Data-Either.html#v:either">Haskell function
	 * {@code either}</a> in Java.
	 *
	 * @param f   function to apply to {@link Left}
	 * @param g   function to apply to {@link Right}
	 * @param <A> type for {@link Left}
	 * @param <B> type for {@link Right}
	 * @param <C> return type of functions
	 * @return function which takes an {@link Either} and returns result of applying the function corresponding to its
	 * type.
	 */
	public static <A, B, C> Function<Either<A, B>, C> either(Function<A, C> f, Function<B, C> g) {
		return e -> e.match(f, g);
	}

	/**
	 * Apply one of given functions to given {@link Either} value, depending on its type.
	 * Case analysis for the {@link Either} type. If the value is {@link Left Left}, apply the first function to the
	 * value; if it is {@link Right Right}, apply the second function.
	 *
	 * @param f   function to apply to {@link Left}
	 * @param g   function to apply to {@link Right}
	 * @param <A> type for {@link Left}
	 * @param <B> type for {@link Right}
	 * @param <C> return type of functions
	 * @return result of applying one of the given functions to given {@link Either} value.
	 * @implNote second implementation of function {@code either} is needed because Java doesn't support partial
	 * application of functions.
	 */
	public static <A, B, C> C either(Function<A, C> f, Function<B, C> g, Either<A, B> e) {
		return e.match(f, g);
	}

	public static <A, B> Stream<A> lefts(Stream<Either<A, B>> eitherStream) {
		return eitherStream
				.filter(e -> e.match(left -> true, right -> false))
				.map(either(
						Function.identity(),
						right -> {
							throw new IllegalStateException("Got a right value after filtering");
						}
				));
	}

	public static <A, B> Stream<B> rights(Stream<Either<A, B>> eitherStream) {
		return eitherStream
				.filter(e -> e.match(left -> false, right -> true))
				.map(either(
						left -> {
							throw new IllegalStateException("Got a left value after filtering");
						},
						Function.identity()
				));
	}

	/**
	 * Pattern match on this {@code Either} and return result of applying the corresponding function.
	 *
	 * @param f   function to apply to {@link Left}
	 * @param g   function to apply to {@link Right}
	 * @param <R> return type of functions
	 */
	public abstract <R> R match(Function<A, R> f, Function<B, R> g);
}
