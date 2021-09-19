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

import java.io.*;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EitherSerializationTest {
	@SuppressWarnings("unchecked")
	private static void testSerializationRoundTrip(Supplier<Either<String, Integer>> supplier, String expected,
			Function<String, String> a, Function<Integer, String> b)
	{
		byte[] bytes;
		try {
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			Either<String, Integer> original = supplier.get();
			ObjectOutputStream output = new ObjectOutputStream(byteStream);
			output.writeObject(original);
			output.flush();
			bytes = byteStream.toByteArray();
		} catch (IOException e) {
			throw new AssertionError(e);
		}
		try {
			ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(bytes));
			Either<String, Integer> deserialized = (Either<String, Integer>) input.readObject();
			assertEquals(expected, deserialized.match(a, b));
		} catch (IOException | ClassNotFoundException e) {
			throw new AssertionError(e);
		}
	}

	@Test
	void testThatLeftCanBeSerialized() {
		testSerializationRoundTrip(() -> Either.left("foo"),
				"foobar",
				s -> s + "bar",
				ignored -> {
					throw new AssertionError("Deserialized Right value");
				}
		);
	}

	@Test
	void testThatRightCanBeSerialized() {
		testSerializationRoundTrip(
				() -> Either.right(42),
				"Right value: 42",
				ignored -> {
					throw new AssertionError("Deserialized Left value");
				},
				i -> "Right value: " + i
		);
	}
}
