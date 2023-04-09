// SPDX-License-Identifier: MIT
package dev.andrybak.util.function;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EitherSerializationTest {
	@SuppressWarnings("unchecked")
	private static void testSerializationRoundTrip(Either<String, Integer> original, String expected,
			Function<String, String> a, Function<Integer, String> b)
	{
		byte[] bytes;
		try {
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
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

	private static <A, B> void testNonSerializable(Either<A, B> original) {
		assertThrows(NotSerializableException.class, () -> {
			ObjectOutputStream output = new ObjectOutputStream(new ByteArrayOutputStream());
			output.writeObject(original);
		});
	}

	@Test
	void testThatLeftCanBeSerialized() {
		testSerializationRoundTrip(Either.left("foo"),
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
				Either.right(42),
				"Right value: 42",
				ignored -> {
					throw new AssertionError("Deserialized Left value");
				},
				i -> "Right value: " + i
		);
	}

	@Test
	void testThatNonSerializableLeftThrows() {
		Either<NonSerializable, String> leftValue = Either.left(new NonSerializable("foo", 42));
		testNonSerializable(leftValue);
	}

	@Test
	void testThatNonSerializableRightThrows() {
		Either<String, NonSerializable> rightValue = Either.right(new NonSerializable("bar", 100));
		testNonSerializable(rightValue);
	}

	static final class NonSerializable {
		final String s;
		final int i;

		NonSerializable(String s, int i) {
			this.s = s;
			this.i = i;
		}
	}
}
