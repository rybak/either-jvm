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
import java.util.stream.Stream;

/**
 * Helper functions to use {@link Either} with {@link Stream}.
 */
public class EitherStreams {
	private EitherStreams() {
		throw new AssertionError();
	}

	/**
	 * @param <A> type for {@link Either.Left}
	 * @param <B> type for {@link Either.Right}
	 * @return {@link Stream} of {@code A} from {@link Either.Left Left} values of given {@link Stream}
	 */
	public static <A, B> Stream<A> lefts(Stream<Either<A, B>> eitherStream) {
		return eitherStream
				.filter(e -> e.match(left -> true, right -> false))
				.map(Either.either(
						Function.identity(),
						right -> {
							throw new IllegalStateException("Got a right value after filtering");
						}
				));
	}

	/**
	 * @param <A> type for {@link Either.Left}
	 * @param <B> type for {@link Either.Right}
	 * @return {@link Stream} of {@code B} from {@link Either.Right Right} values of given {@link Stream}
	 */
	public static <A, B> Stream<B> rights(Stream<Either<A, B>> eitherStream) {
		return eitherStream
				.filter(e -> e.match(left -> false, right -> true))
				.map(Either.either(
						left -> {
							throw new IllegalStateException("Got a left value after filtering");
						},
						Function.identity()
				));
	}
}
