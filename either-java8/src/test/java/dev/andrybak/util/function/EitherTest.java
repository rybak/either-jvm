// SPDX-License-Identifier: MIT
package dev.andrybak.util.function;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EitherTest {
	@Test
	void testThatLeftCanBeConstructed() {
		Either<String, Integer> leftValue = Either.left("Left value");
		assertNotNull(leftValue);
		assertEquals(Either.Left.class, leftValue.getClass());
	}

	@Test
	void testThatRightCanBeConstructed() {
		Either<String, Integer> rightValue = Either.right(42);
		assertNotNull(rightValue);
		assertEquals(Either.Right.class, rightValue.getClass());
	}

	@Test
	void testThatLeftCanBeMatched() {
		Either<String, Integer> leftValue = Either.left("bar");
		assertEquals("foobar", leftValue.match(
				a -> "foo" + a,
				b -> "Right value " + b
		));
	}

	@Test
	void testThatRightCanBeMatched() {
		Either<String, Integer> rightValue = Either.right(42);
		assertEquals("Right value 42", rightValue.match(
				a -> "foo" + a,
				b -> "Right value " + b
		));
	}

	@Test
	void testThatLeftCanBeMatchedByEitherFunction() {
		Either<String, Integer> leftValue = Either.left("bar");
		assertEquals("foobar", Either.either(
				a -> "foo" + a,
				b -> "Right value " + b,
				leftValue
		));
	}

	@Test
	void testThatRightCanBeMatchedByEitherFunction() {
		Either<String, Integer> rightValue = Either.right(42);
		assertEquals("Right value 42", Either.either(
				a -> "foo" + a,
				b -> "Right value " + b,
				rightValue
		));
	}
}
