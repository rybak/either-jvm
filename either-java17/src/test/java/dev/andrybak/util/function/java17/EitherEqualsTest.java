package dev.andrybak.util.function.java17;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EitherEqualsTest {
	private static Stream<Arguments> leftRightValues() {
		return Stream.of(
				Arguments.of("foobar", "foobar"),
				Arguments.of("foo", "bar"),
				Arguments.of(42, 42),
				Arguments.of(42, 0),
				Arguments.of(null, null),
				Arguments.of("foo", 42),
				Arguments.of("foo", null)
		);
	}

	private static Stream<Arguments> equalsValues() {
		return Stream.of(
				Arguments.of("", ""),
				Arguments.of("foobar", "foobar"),
				Arguments.of(42, 42),
				Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE),
				Arguments.of(Integer.MIN_VALUE, Integer.MIN_VALUE)
		);
	}

	private static Stream<Arguments> notEqualsValues() {
		return Stream.of(
				Arguments.of("", "foobar"),
				Arguments.of("foo", "bar"),
				Arguments.of(0, 42),
				Arguments.of(Integer.MAX_VALUE, Integer.MIN_VALUE),
				Arguments.of(new Object(), new Object()),
				Arguments.of("", 42),
				Arguments.of(0, "foo"),
				Arguments.of(new Object(), "bar")
		);
	}

	public static Stream<Arguments> singleObjectValues() {
		return Stream.of(
				Arguments.of("foobar"),
				Arguments.of(42),
				Arguments.of(0),
				Arguments.of(new Object())
		);
	}

	@ParameterizedTest
	@MethodSource("singleObjectValues")
	void testThatNullsAreNotEqual(Object a) {
		assertNotEquals(Either.left(a), Either.left(null));
		assertNotEquals(Either.left(null), Either.left(a));
		assertNotEquals(Either.right(a), Either.right(null));
		assertNotEquals(Either.right(null), Either.right(a));
	}

	@ParameterizedTest
	@MethodSource("leftRightValues")
	void testThatLeftRightAreNotEqual(Object a, Object b) {
		assertNotEquals(Either.left(a), Either.right(b));
		assertNotEquals(Either.right(b), Either.left(a));
		assertNotEquals(Either.right(a), Either.left(b));
		assertNotEquals(Either.left(b), Either.right(a));
	}

	@ParameterizedTest
	@MethodSource("notEqualsValues")
	void testThatNotEqualsWorks(Object a, Object b) {
		assertNotEquals(Either.left(a), Either.left(b));
		assertNotEquals(Either.left(b), Either.left(a));
		assertNotEquals(Either.right(a), Either.right(b));
		assertNotEquals(Either.right(b), Either.right(a));
	}

	@Test
	void testThatNullsAreEqual() {
		assertEquals(Either.left(null), Either.left(null));
		assertEquals(Either.right(null), Either.right(null));
	}

	@ParameterizedTest
	@MethodSource("singleObjectValues")
	void testThatSameObjectsAreEquals(Object a) {
		assertEquals(Either.left(a), Either.left(a));
		assertEquals(Either.right(a), Either.right(a));
	}

	@ParameterizedTest
	@MethodSource("equalsValues")
	void testThatEqualsWorks(Object a, Object b) {
		assertEquals(Either.left(a), Either.left(b));
		assertEquals(Either.left(b), Either.left(a));
		assertEquals(Either.right(a), Either.right(b));
		assertEquals(Either.right(b), Either.right(a));
	}
}
