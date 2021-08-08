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