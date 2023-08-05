// SPDX-License-Identifier: MIT
package dev.andrybak.util.function.java8;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Implementation of functional programming abstraction {@code Either} for Java 8 and later versions.
 * <p>
 * The class {@code Either} represents values with two possibilities: an object of type {@code Either<A, B>} contains
 * either a value of type {@code A} ({@link Left Left} alternative) or of type {@code B} ({@link Right Right}
 * alternative). This type is often used to represent a result of an operation that may result in an error,
 * for example, {@code Either<Exception, Result>}.
 * </p>
 * <p>
 * The most basic and most important part of API of this class is instance method {@link #match(Function, Function)}.
 * It allows to structurally pattern match on the left and right alternatives and to access the values stored in
 * objects of type {@code Either}.
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
	 * Returns a {@link Left} containing given value of type {@code A}.
	 *
	 * @param <A> type for returned {@link Left}
	 * @param <B> type for corresponding {@link Right}
	 * @param a   value to be stored in the returned {@link Left}
	 * @return a {@link Left} with the given value
	 */
	public static <A, B> Either<A, B> left(A a) {
		return new Left<>(a);
	}

	/**
	 * Returns a {@link Right} containing given value of type {@code B}.
	 *
	 * @param <A> type for corresponding {@link Left}
	 * @param <B> type for returned {@link Right}
	 * @param b   value to be stored in the returned {@link Right}
	 * @return a {@link Right} with the given value
	 */
	public static <A, B> Either<A, B> right(B b) {
		return new Right<>(b);
	}

	/**
	 * Converts two functions, one which takes {@code A} and returns {@code C} and another which takes {@code B} and
	 * returns {@code C}, into a {@link Function} that takes an {@code Either<A, B>} and returns {@code C}.
	 * This can be useful for usage with {@link java.util.stream.Stream} API.
	 * <p>
	 * Implementation of
	 * <a href="https://hackage.haskell.org/package/base/docs/Data-Either.html#v:either">Haskell function
	 * {@code either}</a> in Java.
	 *
	 * @param f   function to apply to a value of {@link Left}
	 * @param g   function to apply to a value of {@link Right}
	 * @param <A> type for {@link Left}
	 * @param <B> type for {@link Right}
	 * @param <C> return type of functions
	 * @return function which takes an {@link Either} and returns result of applying one of the given functions
	 * corresponding to the type of given {@link Either}.
	 */
	public static <A, B, C> Function<Either<? extends A, ? extends B>, C> either(
			Function<? super A, ? extends C> f,
			Function<? super B, ? extends C> g)
	{
		return e -> e.match(f, g);
	}

	/**
	 * If given {@link Either} is {@link Left Left}, returns result of applying the first given function to its value.
	 * If given {@link Either} is {@link Right Right}, returns result of applying the second given function to its
	 * value.
	 *
	 * @param f   function to apply to a value of {@link Left}
	 * @param g   function to apply to a value of {@link Right}
	 * @param e   an {@link Either} to apply the given functions to
	 * @param <A> type for {@link Left}
	 * @param <B> type for {@link Right}
	 * @param <C> return type of functions
	 * @return result of applying one of the given functions to given {@link Either} value.
	 * @implNote second implementation of function {@code either} is needed because Java doesn't support partial
	 * application of functions.
	 */
	public static <A, B, C> C either(
			Function<? super A, ? extends C> f,
			Function<? super B, ? extends C> g,
			Either<? extends A, ? extends B> e)
	{
		return e.match(f, g);
	}

	/**
	 * Pattern matches on this {@link Either} and returns result of applying the corresponding function.
	 *
	 * @param f   function to apply to a value of {@link Left}
	 * @param g   function to apply to a value of {@link Right}
	 * @param <R> return type of functions
	 * @return result of applying one of the functions to this {@link Either}
	 */
	public abstract <R> R match(Function<? super A, ? extends R> f, Function<? super B, ? extends R> g);

	/**
	 * If this {@link Either} is {@link Left Left}, performs the first given action with its value.
	 * If this {@link Either} is {@link Right Right}, performs the second given action with its value.
	 *
	 * @param f consumer to apply to a value of {@link Left}
	 * @param g consumer to apply to a value of {@link Right}
	 */
	public abstract void accept(Consumer<? super A> f, Consumer<? super B> g);

	/**
	 * If this {@link Either} is {@link Left Left}, performs the first given action with its value and returns this
	 * {@link Either}. If this {@link Either} is a {@link Right Right}, performs the second given action with its
	 * value and returns this {@link Either}.
	 * <p>
	 * This method is useful for adding logging or debugging statements in the middle of invocation of method
	 * {@link #match(Function, Function)}:
	 *
	 * <pre>{@code
	 * return getEither()
	 *     .peek(a -> System.out.println("Got left " + a), b -> System.out.println("Got right " + b));
	 *     .match(...);
	 * }</pre>
	 *
	 * @param f consumer to apply to {@link Left}
	 * @param g consumer to apply to {@link Right}
	 * @return this {@link Either}
	 */
	public abstract Either<A, B> peek(Consumer<? super A> f, Consumer<? super B> g);

	/**
	 * Left alternative of the {@link Either Either&lt;A, B&gt;} type, containing a value of type {@code A}.
	 *
	 * @param <A> type of the value in this {@link Left}
	 * @param <B> type of the value in the corresponding {@link Right}
	 */
	static final class Left<A, B> extends Either<A, B> {
		/**
		 * The value of this {@link Left}.
		 */
		private final A a;

		private Left(A a) {
			this.a = a;
		}

		@Override
		public <R> R match(Function<? super A, ? extends R> f, Function<? super B, ? extends R> g) {
			return f.apply(a);
		}

		@Override
		public void accept(Consumer<? super A> f, Consumer<? super B> g) {
			f.accept(a);
		}

		public Either<A, B> peek(Consumer<? super A> f, Consumer<? super B> g) {
			f.accept(a);
			return this;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;

			Left<?, ?> left = (Left<?, ?>) o;

			return Objects.equals(a, left.a);
		}

		@Override
		public int hashCode() {
			return a != null ? (31 * a.hashCode()) : 0;
		}

		@Override
		public String toString() {
			return "Left(" + a + ')';
		}
	}

	/**
	 * Right alternative of the {@link Either Either&lt;A, B&gt;} type, containing a value of type {@code B}.
	 *
	 * @param <A> type of the value in the corresponding {@link Left}
	 * @param <B> type of the value in this {@link Right}
	 */
	static final class Right<A, B> extends Either<A, B> {
		/**
		 * The value of this {@link Right}.
		 */
		private final B b;

		private Right(B b) {
			this.b = b;
		}

		@Override
		public <R> R match(Function<? super A, ? extends R> f, Function<? super B, ? extends R> g) {
			return g.apply(b);
		}

		@Override
		public void accept(Consumer<? super A> f, Consumer<? super B> g) {
			g.accept(b);
		}

		@Override
		public Either<A, B> peek(Consumer<? super A> f, Consumer<? super B> g) {
			g.accept(b);
			return this;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;

			Right<?, ?> right = (Right<?, ?>) o;

			return Objects.equals(b, right.b);
		}

		@Override
		public int hashCode() {
			return b != null ? (37 * b.hashCode()) : 0;
		}

		@Override
		public String toString() {
			return "Right(" + b + ')';
		}
	}
}
