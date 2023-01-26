// SPDX-License-Identifier: MIT
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
