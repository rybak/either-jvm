/*
 * Copyright (C) 2021 Andrei Rybak
 *
 * This file is part of Either-JVM library.
 *
 * Either-JVM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <https://www.gnu.org/licenses/>.
 */
package dev.andrybak.util.function;

import java.util.function.Function;

/**
 * Java implementation of functional abstraction {@code Either}. Inspired by
 * <a href="https://hackage.haskell.org/package/base/docs/Prelude.html#t:Either">Haskell's type {@code Either}</a>.
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

	public static <A, B> Either<A, B> left(A a) {
		return new Left<>(a);
	}

	public static <A, B> Either<A, B> right(B b) {
		return new Right<>(b);
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

	public static class Right<A, B> extends Either<A, B> {
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
