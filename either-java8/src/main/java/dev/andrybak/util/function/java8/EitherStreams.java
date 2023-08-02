// SPDX-License-Identifier: MIT
package dev.andrybak.util.function.java8;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Helper functions to use {@link Either} with {@link Stream}s.
 */
public class EitherStreams {
	private EitherStreams() {
		throw new AssertionError();
	}

	/**
	 * Returns a stream consisting of objects of type {@code A} taken from elements of given stream of {@link Either}
	 * that are {@link Either.Left}.
	 *
	 * @param <A> type for {@link Either.Left}
	 * @param <B> type for {@link Either.Right}
	 * @return {@link Stream} of {@code A} from {@link Either.Left Left} values of given {@link Stream}
	 */
	public static <A, B> Stream<A> lefts(Stream<? extends Either<? extends A, ? extends B>> eitherStream) {
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
	 * Returns a stream consisting of objects of type {@code B} taken from elements of given stream of {@link Either}
	 * that are {@link Either.Right}.
	 *
	 * @param <A> type for {@link Either.Left}
	 * @param <B> type for {@link Either.Right}
	 * @return {@link Stream} of {@code B} from {@link Either.Right Right} values of given {@link Stream}
	 */
	public static <A, B> Stream<B> rights(Stream<? extends Either<? extends A, ? extends B>> eitherStream) {
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
