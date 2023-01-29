// SPDX-License-Identifier: MIT
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
