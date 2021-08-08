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

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * <p>
 * Note, that for implementations of functions for various Haskell type classes, we use a non-existent notation of
 * partially applied types for generics. The type of left values ({@code <A>} of the generics of the class) is fixed in
 * these type classes, while type of right values ({@code <B>}) changes. For that purpose, a fixed {@code <E>} type
 * value is used in the documentation, like so: {@code Either<E>}.
 * </p>
 */
public class EitherTypeClasses {
	private EitherTypeClasses() {
		throw new AssertionError();
	}

	/**
	 * Implementation of the {@code map} function of the
	 * <a href="https://hackage.haskell.org/package/base/docs/src/GHC-Base.html#Functor">Functor</a> abstraction for
	 * {@code Either<E>}.
	 */
	public static <E, A, B> Either<E, B> map(Function<A, B> f, Either<E, A> e) {
		return e.match(
				Either::left,
				b -> Either.right(f.apply(b))
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
	 * <a href="https://hackage.haskell.org/package/base/docs/src/GHC-Base.html#Applicative">Applicative</a> abstraction
	 * for {@code Either<E>}.
	 */
	public static <E, A> Either<E, A> pure(A a) {
		return Either.right(a);
	}

	/**
	 * Sequential application. Part of the implementation of the
	 * <a href="https://hackage.haskell.org/package/base/docs/src/GHC-Base.html#Applicative">Applicative</a> abstraction
	 * for {@code Either<E>}.
	 *
	 * @param ef  {@code Either} of function
	 * @param ea  {@code Either} of possible value of type {@code A}
	 * @param <E> type of {@link Either.Left}
	 * @param <A> initial type of {@link Either.Right}
	 * @param <B> type of {@link Either.Right} after applying the function.
	 */
	public static <E, A, B> Either<E, B> sequential(Either<E, Function<A, B>> ef, Either<E, A> ea) {
		return ef.match(
				Either::left,
				f -> map(f, ea)
		);
	}

	/**
	 * @implNote second implementation of function {@code sequential} is needed because Java doesn't support partial
	 * application of functions.
	 */
	public static <E, A, B> Function<Either<E, A>, Either<E, B>> sequential(Either<E, Function<A, B>> ef) {
		return ea -> ef.match(
				Either::left,
				f -> map(f, ea)
		);
	}

	/**
	 * Lift a binary function to {@code Either<E>}. Part of the implementation of the
	 * <a href="https://hackage.haskell.org/package/base/docs/src/GHC-Base.html#Applicative">Applicative</a> abstraction
	 * for {@code Either<E>}.
	 */
	public static <E, A, B, C> Either<E, C> liftA2(BiFunction<A, B, C> f, Either<E, A> ea, Either<E, B> eb) {
		return ea.match(
				Either::left,
				rightA -> eb.match(
						Either::left,
						rightB -> Either.right(f.apply(rightA, rightB))
				)
		);
	}

	/**
	 * @implNote second implementation of function {@code liftA2} is needed because Java doesn't support partial
	 * application of functions.
	 */
	public static <E, A, B, C> BiFunction<Either<E, A>, Either<E, B>, Either<E, C>> liftA2(BiFunction<A, B, C> f) {
		return (ea, eb) -> ea.match(
				Either::left,
				rightA -> eb.match(
						Either::left,
						rightB -> Either.right(f.apply(rightA, rightB))
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
				Either::left,
				f
		);
	}
}
