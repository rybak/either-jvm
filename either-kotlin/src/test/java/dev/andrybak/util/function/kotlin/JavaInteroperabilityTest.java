package dev.andrybak.util.function.kotlin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

public class JavaInteroperabilityTest {
	/**
	 * Ensures compatibility between Kotlin class {@link Either} and Java.
	 */
	@Test
	void testThatEitherIsCompatibleWithJava() {
		Either<String, Integer> leftValueCompanion = Either.Companion.left("hello");
		Either<String, Integer> leftValueStatic = Either.left("world");
		Either<String, Integer> rightValueCompanion = Either.Companion.right(42);
		Either<String, Integer> rightValueStatic = Either.right(42);
		Stream.of(leftValueCompanion, leftValueStatic, rightValueCompanion, rightValueStatic)
				.map(e -> e.match(s -> "String: " + s, i -> "Int: " + i))
				.forEach(s -> Assertions.assertTrue(
						s.startsWith("String: ") || s.startsWith("Int: ")
				));
	}
}
