package dev.andrybak.util.function.java8;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EitherHashCodeTest {
	@Test
	void test() {
		Map<Either<String, Integer>, String> m = new HashMap<>();
		Either<String, Integer> leftValue = Either.left("foo");
		Either<String, Integer> rightValue = Either.right(42);
		assertNull(m.put(leftValue, "bar"));
		assertNull(m.put(rightValue, "hello"));
		assertEquals(2, m.size());

		assertTrue(m.containsKey(Either.left("foo")));
		assertTrue(m.containsKey(leftValue));
		assertEquals("bar", m.get(Either.left("foo")));
		assertEquals("bar", m.get(leftValue));
		assertEquals("bar", m.remove(Either.left("foo")));
		assertFalse(m.containsKey(leftValue));
		assertNull(m.get(leftValue));

		assertTrue(m.containsKey(Either.right(42)));
		assertTrue(m.containsKey(rightValue));
		assertEquals("hello", m.get(Either.right(42)));
		assertEquals("hello", m.get(rightValue));
		assertEquals("hello", m.put(Either.right(42), "world"));
		assertEquals("world", m.get(Either.right(42)));
		assertEquals("world", m.get(rightValue));
		assertTrue(m.containsKey(Either.right(42)));
		assertTrue(m.containsKey(rightValue));
		assertEquals(1, m.size());
	}
}
