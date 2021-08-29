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
package dev.andrybak.util.function.java17;

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
 * Inspired by
 * <a href="https://hackage.haskell.org/package/base/docs/Prelude.html#t:Either">Haskell's type {@code Either}</a>.
 * </p>
 *
 * @param <A> type for {@link Left}
 * @param <B> type for {@link Right}
 */
public sealed abstract class Either<A, B> permits Left, Right {
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
	 * <a href="https://hackage.haskell.org/package/base/docs/src/GHC-Base.html#Functor">Functor</a> abstraction for
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
	 * <a href="https://hackage.haskell.org/package/base/docs/src/GHC-Base.html#Applicative">Applicative</a>
	 * abstraction for {@code Either<E>}.
	 */
	public static <E, A> Either<E, A> pure(A a) {
		return right(a);
	}

	/**
	 * Sequential application. Part of the implementation of the
	 * <a href="https://hackage.haskell.org/package/base/docs/src/GHC-Base.html#Applicative">Applicative</a>
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
	 * <a href="https://hackage.haskell.org/package/base/docs/src/GHC-Base.html#Applicative">Applicative</a>
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
	 * <a href="https://hackage.haskell.org/package/base/docs/src/GHC-Base.html#Monad">Monad</a>
	 * abstraction for {@code Either<E>}.
	 */
	public static <E, A, B> Either<E, B> bind(Either<E, A> e, Function<A, Either<E, B>> f) {
		return e.match(
				Left::new,
				f
		);
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
