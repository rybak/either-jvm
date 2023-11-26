// SPDX-License-Identifier: MIT
package dev.andrybak.util.function.samples;

import dev.andrybak.util.function.java17.Either;
import dev.andrybak.util.function.java17.Left;
import dev.andrybak.util.function.java17.Right;

import java.util.List;

public class PatternMatchingSwitch {
	public static void main(String[] args) {
		List<Either<String, Integer>> xs = List.of(
				Either.left("hello"),
				Either.left("world"),
				Either.right(42)
		);
		xs.forEach(x -> {
			/*
			 * How to use method `match` of class `Either` to pattern match between alternatives.
			 */
			String matchRes = x.match(
					s -> "String: " + s,
					i -> "Int: " + i
			);
			/*
			 * Add explicit types to lambda parameters to make the code clearer, if needed.
			 */
			String explicitLambdaParameters = x.match(
					(String s) -> "Explicitly typed String: " + s,
					(Integer i) -> "Explicitly typed Int: " + i
			);
			/*
			 * This is only available in Java 21 (or in preview in Java 17)
			 */
			String switchRes = switch (x) {
				case Left<String, Integer> left -> "String: " + left.a();
				case Right<String, Integer> right -> "Int: " + right.b();
			};
			System.out.println(matchRes);
			System.out.println(explicitLambdaParameters);
			System.out.println(switchRes);
		});
	}
}
