package dev.andrybak.util.function

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class EitherTest {
	@Test
	internal fun testThatLeftCanBeConstructed() {
		val leftValue: Either<String, Int> = Either.left("Hello, world!")
		assertNotNull(leftValue)
		assertEquals(Either.Left::class, leftValue::class)
	}

	@Test
	internal fun testThatRightCanBeConstructed() {
		val rightValue: Either<String, Int> = Either.right(42)
		assertNotNull(rightValue)
		assertEquals(Either.Right::class, rightValue::class)
	}

	@Test
	internal fun testThatLeftCanBeMatchedByWhen() {
		val leftValue: Either<String, Int> = Either.left("bar")
		assertEquals(
			"foobar",
			when (leftValue) {
				is Either.Left -> "foo" + leftValue.leftValue
				is Either.Right -> "Right value " + leftValue.rightValue
			}
		)
	}

	@Test
	internal fun testThatRightCanBeMatchedByWhen() {
		val rightValue: Either<String, Int> = Either.right(42)
		assertEquals(
			"Right value 42",
			when (rightValue) {
				is Either.Left -> "foo" + rightValue.leftValue
				is Either.Right -> "Right value " + rightValue.rightValue
			}
		)
	}

	@Test
	internal fun testThatLeftCanBeMatchedByEitherFunction() {
		val leftValue: Either<String, Int> = Either.left("bar")
		assertEquals("foobar", either({ a -> "foo$a" }, { b -> "Right value $b" }, leftValue))
	}

	@Test
	internal fun testThatRightCanBeMatchedByEitherFunction() {
		val rightValue: Either<String, Int> = Either.right(42)
		assertEquals("Right value 42", either({ a -> "foo$a" }, { b -> "Right value $b" }, rightValue))
	}

	@Test
	internal fun testThatLeftCanBeMatchedByEitherFunction2() {
		val leftValue: Either<String, Int> = Either.left("bar")
		val f: (Either<String, Int>) -> String = either({ a -> "foo$a" }, { b -> "Right value $b" })
		assertEquals("foobar", f(leftValue))
	}

	@Test
	internal fun testThatRightCanBeMatchedByEitherFunction2() {
		val rightValue: Either<String, Int> = Either.right(42)
		val f: (Either<String, Int>) -> String = either({ a -> "foo$a" }, { b -> "Right value $b" })
		assertEquals("Right value 42", f(rightValue))
	}
}
