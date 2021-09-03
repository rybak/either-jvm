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
}