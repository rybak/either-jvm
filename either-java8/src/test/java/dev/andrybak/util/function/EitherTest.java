// SPDX-License-Identifier: MIT
package dev.andrybak.util.function;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertAll;
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

	@Test
	void testThatMatchAcceptsFunctionWithSuperClassInput() {
		assertAll(() -> {
			Either<String, Integer> leftValue = Either.left("bar");
			Function<CharSequence, String> f = cs -> "foo" + cs.toString();
			assertEquals("foobar", leftValue.match(
					f,
					b -> "Right value " + b
			));
		}, () -> {
			Either<String, Integer> rightValue = Either.right(42);
			Function<Number, String> g = n -> n.toString() + "bar";
			assertEquals("42bar", rightValue.match(
					a -> "Left value " + a,
					g
			));
		});
	}

	@Test
	void testThatMatchAcceptsFunctionWithSubClassOutput() {
		// dummy is used to force the return type to be `Collection<String>`
		Collection<String> dummy = Collections.emptySet();
		assertAll(() -> {
			Either<String, Integer> leftValue = Either.left("bar");
			Function<String, List<String>> f = s -> Arrays.asList("foo", s);
			assertEquals(Arrays.asList("foo", "bar"), leftValue.match(
					f,
					(Integer b) -> dummy
			));
		}, () -> {
			Either<String, Integer> rightValue = Either.right(42);
			Function<Integer, List<String>> g = i -> Arrays.asList(i.toString(), "bar");
			assertEquals(Arrays.asList("42", "bar"), rightValue.match(
					(String a) -> dummy,
					g
			));
		});
	}

	@Test
	void testThatEitherAcceptsFunctionWithSuperClassInput() {
		assertAll("either(f,g)", () -> {
			Either<String, Integer> leftValue = Either.left("bar");
			Function<CharSequence, String> takesSuperclass = cs -> "foo" + cs.toString();
			assertEquals(
					"foobar",
					Either.either(takesSuperclass, b -> "Right value " + b).apply(leftValue)
			);
		}, () -> {
			Either<String, Integer> rightValue = Either.right(42);
			Function<Number, String> takesSuperClass = n -> n.toString() + "bar";
			assertEquals(
					"42bar",
					Either.either(a -> "Left value " + a, takesSuperClass).apply(rightValue)
			);
		});
		assertAll("either(f,g,e)", () -> {
			Either<String, Integer> leftValue = Either.left("bar");
			Function<CharSequence, String> takesSuperclass = cs -> "foo" + cs.toString();
			assertEquals(
					"foobar",
					Either.either(takesSuperclass, b -> "Right value " + b, leftValue)
			);
		}, () -> {
			Either<String, Integer> rightValue = Either.right(42);
			Function<Number, String> takesSuperClass = n -> n.toString() + "bar";
			assertEquals(
					"42bar",
					Either.either(a -> "Left value " + a, takesSuperClass, rightValue)
			);
		});
	}

	@Test
	void testThatEitherAcceptsEitherWithSubClass() {
		class SuperClass {
			@Override
			public String toString() {
				return "SuperClass";
			}
		}
		class SubClass extends SuperClass {
			@Override
			public String toString() {
				return "SubClass";
			}
		}
		assertAll(() -> {
			Either<SubClass, Integer> leftValue = Either.left(new SubClass());
			Function<SuperClass, String> takesSuperclass = superObj -> "foo" + superObj.toString();
			Function<Integer, String> g = (Integer b) -> "Right value " + b;
			assertEquals(
					"fooSubClass",
					Either.<SuperClass, Integer, String>either(takesSuperclass, g, leftValue)
			);
		}, () -> {
			Either<Integer, SubClass> rightValue = Either.right(new SubClass());
			Function<Integer, String> f = (Integer b) -> "Left value " + b;
			Function<SuperClass, String> takesSuperclass = superObj -> "foo" + superObj.toString();
			assertEquals(
					"fooSubClass",
					Either.<Integer, SuperClass, String>either(f, takesSuperclass, rightValue)
			);
		});
	}
}
