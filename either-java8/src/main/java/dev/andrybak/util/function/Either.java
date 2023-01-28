// SPDX-License-Identifier: MIT
package dev.andrybak.util.function;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Java implementation of functional programming abstraction {@code Either} for Java 8.
 * <p>
 * The most basic and most important part of API of this class is instance method {@link #match(Function, Function)}.
 * It allows to structurally pattern match on the left and right values stored in objects of type {@code Either}.
 * </p>
 * <p>
 * Inspired by
 * <a href="https://hackage.haskell.org/package/base/docs/Data-Either.html">Haskell's type {@code Either}</a>.
 * </p>
 * <p>
 * If both types {@code A} and {@code B} are {@link Serializable} then {@code Either<A, B>} can be serialized.
 * </p>
 *
 * @param <A> type for {@link Left}
 * @param <B> type for {@link Right}
 */
public abstract class Either<A, B> implements Serializable {
	/**
	 * Private to prevent inheritance from outside.
	 */
	private Either() {
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

	/**
	 * Pattern match on this {@code Either} and return result of applying the corresponding function.
	 *
	 * @param f   function to apply to {@link Left}
	 * @param g   function to apply to {@link Right}
	 * @param <R> return type of functions
	 */
	public abstract <R> R match(Function<A, R> f, Function<B, R> g);

	public static final class Left<A, B> extends Either<A, B> {
		private final A a;

		private Left(A a) {
			this.a = a;
		}

		@Override
		public <R> R match(Function<A, R> f, Function<B, R> g) {
			return f.apply(a);
		}
	}

	public static final class Right<A, B> extends Either<A, B> {
		private final B b;

		private Right(B b) {
			this.b = b;
		}

		@Override
		public <R> R match(Function<A, R> f, Function<B, R> g) {
			return g.apply(b);
		}
	}
}
