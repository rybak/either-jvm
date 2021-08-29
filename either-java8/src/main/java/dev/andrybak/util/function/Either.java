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
package dev.andrybak.util.function;

import java.util.function.Function;

/**
 * Java implementation of functional programming abstraction {@code Either} for Java 8.
 * <p>
 * The most basic and most important part of API of this class is instance method {@link #match(Function, Function)}.
 * It allows to structurally pattern match on the left and right values stored in objects of type {@code Either}.
 * </p>
 * <p>
 * Inspired by
 * <a href="https://hackage.haskell.org/package/base/docs/Prelude.html#t:Either">Haskell's type {@code Either}</a>.
 * </p>
 *
 * @param <A> type for {@link Left}
 * @param <B> type for {@link Right}
 */
public abstract class Either<A, B> {
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
	 * Java implementation of
	 * <a href="https://hackage.haskell.org/package/base-4.15.0.0/docs/Prelude.html#v:either">Haskell function
	 * {@code either}</a>. Useful for usage with {@link java.util.stream.Stream} API.
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
