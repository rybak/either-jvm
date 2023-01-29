// SPDX-License-Identifier: MIT
package dev.andrybak.util.function

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class EitherCollectionsTest {

	@Test
	fun testThatLeftsWorks() {
		val actual: List<String> = listOf<Either<String, Int>>(
			Either.left("Hello"),
			Either.right(0),
			Either.left("World"),
			Either.right(1),
			Either.left("foobar"),
			Either.right(2)
		).lefts()
		assertEquals(listOf("Hello", "World", "foobar"), actual)
	}

	@Test
	fun testThatRightsWorks() {
		val actual: List<Int> = listOf<Either<String, Int>>(
			Either.left("Hello"),
			Either.right(0),
			Either.left("World"),
			Either.right(1),
			Either.left("foobar"),
			Either.right(2)
		).rights()
		assertEquals(listOf(0, 1, 2), actual)
	}

	@Test
	fun testThatLeftsAcceptsStreamsOfSubClass() {
		val input: List<Either<String, Int>> = listOf(
			Either.left("Hello"),
			Either.right(0),
			Either.left("World"),
			Either.right(1),
			Either.left("foobar"),
			Either.right(2)
		)
		val actual: List<CharSequence> = input.lefts<CharSequence, Number>()
		assertEquals(listOf("Hello", "World", "foobar"), actual)
	}

	@Test
	fun testThatRightsAcceptsStreamsOfSubclass() {
		val input: List<Either<String, Int>> = listOf(
			Either.right(0),
			Either.left("Hello"),
			Either.left("World"),
			Either.left("foobar"),
			Either.right(1),
			Either.right(2)
		)
		val actual: List<Number> = input.rights<CharSequence, Number>()
		assertEquals(listOf(0, 1, 2), actual)
	}
}