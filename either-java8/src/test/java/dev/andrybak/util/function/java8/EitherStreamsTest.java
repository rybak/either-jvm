// SPDX-License-Identifier: MIT
package dev.andrybak.util.function.java8;

import dev.andrybak.util.function.java8.Either;
import dev.andrybak.util.function.java8.EitherStreams;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EitherStreamsTest {
	@Test
	void testThatLeftsWorks() {
		List<String> actual = EitherStreams.lefts(Stream.of(
				Either.left("Hello"),
				Either.right(0),
				Either.left("World"),
				Either.right(1),
				Either.left("foobar"),
				Either.right(2)
		)).collect(toList());
		assertEquals(Arrays.asList("Hello", "World", "foobar"), actual);
	}

	@Test
	void testThatRightsWorks() {
		List<Integer> actual = EitherStreams.rights(Stream.of(
				Either.right(0),
				Either.left("Hello"),
				Either.left("World"),
				Either.left("foobar"),
				Either.right(1),
				Either.right(2)
		)).collect(toList());
		assertEquals(Arrays.asList(0, 1, 2), actual);
	}

	@Test
	void testThatLeftsAcceptsStreamsOfSubClass() {
		Stream<Either<String, Integer>> input = Stream.of(
				Either.left("Hello"),
				Either.right(0),
				Either.left("World"),
				Either.right(1),
				Either.left("foobar"),
				Either.right(2)
		);
		Stream<CharSequence> lefts = EitherStreams.<CharSequence, Number>lefts(input);
		List<CharSequence> actual = lefts.collect(toList());
		assertEquals(Arrays.asList("Hello", "World", "foobar"), actual);
	}

	@Test
	void testThatRightsAcceptsStreamsOfSubclass() {
		Stream<Either<String, Integer>> inputs = Stream.of(
				Either.right(0),
				Either.left("Hello"),
				Either.left("World"),
				Either.left("foobar"),
				Either.right(1),
				Either.right(2)
		);
		Stream<Number> rights = EitherStreams.<CharSequence, Number>rights(inputs);
		List<Number> actual = rights.collect(toList());
		assertEquals(Arrays.asList(0, 1, 2), actual);
	}
}
