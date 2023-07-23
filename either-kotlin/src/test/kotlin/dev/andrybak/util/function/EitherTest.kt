package dev.andrybak.util.function

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

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
	internal fun testThatLeftCanBeMatchedByMatch() {
		val leftValue: Either<String, Int> = Either.left("bar")
		val f: (String) -> String = { a -> "foo$a" }
		val g: (Int) -> String = { b -> "Right value $b" }
		assertEquals("foobar", leftValue.match(f, g))
	}

	@Test
	internal fun testThatRightCanBeMatchedByMatch() {
		val rightValue: Either<String, Int> = Either.right(42)
		val f: (String) -> String = { a -> "foo$a" }
		val g: (Int) -> String = { b -> "Right value $b" }
		assertEquals("Right value 42", rightValue.match(f, g))
	}

	@Test
	internal fun testThatLeftCanBeMatchedByMatchWithMethodRef() {
		val leftValue: Either<String, Int> = Either.left("bar")
		assertEquals("foobar", leftValue.match(::foo, ::bar))
	}

	@Test
	internal fun testThatRightCanBeMatchedByMatchWithMethodRef() {
		val rightValue: Either<String, Int> = Either.right(42)
		assertEquals("Right value 42", rightValue.match(::foo, ::bar))
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

	@Test
	internal fun testThatEitherOfSubClassesCanBeUsedAsParameter() {
		val f: (Either<CharSequence, Number>) -> Unit = { e ->
			when (e) {
				is Either.Left -> assertInstanceOf(CharSequence::class.java, e.leftValue)
				is Either.Right -> assertInstanceOf(Number::class.java, e.rightValue)
			}
		}
		val leftValue: Either<String, Int> = Either.left("foo")
		val rightValue: Either<String, Int> = Either.right(42)
		f.invoke(leftValue)
		f.invoke(rightValue)
	}

	@Test
	internal fun testThatEitherAcceptsFunctionWithSuperClassInput() {
		val f: (CharSequence) -> String = { cs -> "foo$cs" }
		val g: (Number) -> String = { n -> "${n}bar" }
		val leftValue: Either<String, Int> = Either.left("bar")
		assertEquals("foobar", either(f, g, leftValue))
		assertEquals("foobar", either(f, g).invoke(leftValue))
		val rightValue: Either<String, Int> = Either.right(42)
		assertEquals("42bar", either(f, g, rightValue))
		assertEquals("42bar", either(f, g).invoke(rightValue))
	}

	@Test
	internal fun testThatEitherAcceptsFunctionWithSubClassOutput() {
		assertAll({
			val f: (String) -> List<String> = { cs -> listOf("foo", cs) }
			val dummy: (Int) -> Collection<String> = { _ -> emptySet() }
			val leftValue: Either<String, Int> = Either.left("bar")
			assertEquals(listOf("foo", "bar"), either(f, dummy, leftValue))
			assertEquals(listOf("foo", "bar"), either(f, dummy).invoke(leftValue))
		}, {
			val dummy: (String) -> Collection<String> = { _ -> emptySet() }
			val g: (Int) -> List<String> = { n -> listOf(n.toString(), "bar") }
			val rightValue: Either<String, Int> = Either.right(42)
			assertEquals(listOf("42", "bar"), either(dummy, g).invoke(rightValue))
			assertEquals(listOf("42", "bar"), either(dummy, g, rightValue))
		})
	}

	@Test
	fun testAcceptOnLeft() {
		val leftValue: Either<String, Int> = Either.left("foo")
		var leftCount = 0
		leftValue.accept(
			{ a: String ->
				leftCount++
				assertEquals("foo", a)
			},
			{ _: Int -> Assertions.fail<Any>("unexpected Right") }
		)
		assertEquals(1, leftCount)
	}

	@Test
	fun testAcceptOnRight() {
		val rightValue: Either<String, Int> = Either.right(42)
		var rightCount = 0
		rightValue.accept(
			{ _: String ->
				Assertions.fail<Any>(
					"unexpected Left"
				)
			},
			{ b: Int? ->
				rightCount++
				assertEquals(42, b)
			}
		)
		assertEquals(1, rightCount)
	}

	@Test
	fun testPeekOnLeft() {
		val leftValue: Either<String, Int> = Either.left("foo")
		var leftCount = 0
		val res: Either<String, Int> = leftValue.peek(
			{ a: String ->
				leftCount++
				assertEquals("foo", a)
			},
			{ _: Int ->
				Assertions.fail("unexpected Right")
			}
		)
		assertEquals(1, leftCount)
		Assertions.assertSame(leftValue, res)
	}

	@Test
	fun testPeekOnRight() {
		val rightValue: Either<String, Int> = Either.right(42)
		var rightCount = 0
		val res: Either<String, Int> = rightValue.peek(
			{ _: String ->
				Assertions.fail("unexpected Left")
			},
			{ b: Int ->
				rightCount++
				assertEquals(42, b)
			}
		)
		Assertions.assertSame(rightValue, res)
		assertEquals(1, rightCount)
	}

	companion object {
		fun foo(s: String): String = "foo$s"
		private fun bar(i: Int): String = "Right value $i"
	}
}
