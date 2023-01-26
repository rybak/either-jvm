// SPDX-License-Identifier: MIT
package dev.andrybak.util.function;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.*;

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
}