// SPDX-License-Identifier: MIT
package dev.andrybak.util.function.java17;

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
	 * that are {@link Left}.
	 *
	 * @param <A>          type for {@link Left}
	 * @param <B>          type for {@link Right}
	 * @param eitherStream a {@link Stream} of {@link Either Either&lt;A, B&gt;}
	 * @return {@link Stream Stream&lt;A&gt;} from {@link Left} values of given {@link Stream}
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
	 * that are {@link Right}.
	 *
	 * @param <A>          type for {@link Left}
	 * @param <B>          type for {@link Right}
	 * @param eitherStream a {@link Stream} of {@link Either Either&lt;A, B&gt;}
	 * @return {@link Stream Stream&lt;B&gt;} from {@link Right} values of given {@link Stream}
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
