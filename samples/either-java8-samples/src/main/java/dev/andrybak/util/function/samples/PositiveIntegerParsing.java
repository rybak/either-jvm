// SPDX-License-Identifier: MIT
package dev.andrybak.util.function.samples;

import dev.andrybak.util.function.java8.Either;

import java.util.function.Function;
import java.util.regex.Pattern;

public class PositiveIntegerParsing {
	/**
	 * Integer.MAX_VALUE == 2_147_483_647, therefore maximum 10 digits are allowed
	 */
	private static final Pattern POSITIVE_INTEGER_REGEX = Pattern.compile("[1-9][0-9]{0,9}");
	private static final int MAX_INT_STR_LEN = Integer.toString(Integer.MAX_VALUE).length();

	/**
	 * Parses positive integers that will fit into {@link Integer#MAX_VALUE}.
	 *
	 * @param s input
	 * @return either an error message (a {@link String}) or the {@link Integer} parsed from the input
	 */
	static Either<String, Integer> parsePositiveInteger(String s) {
		if (s == null) {
			return Either.left("Cannot parse null");
		}
		if (s.isEmpty()) {
			return Either.left("Cannot parse an empty string");
		}
		if (s.length() > MAX_INT_STR_LEN) {
			return Either.left("The string is too long: '" + s + "'");
		}
		if (!POSITIVE_INTEGER_REGEX.matcher(s).matches()) {
			return Either.left("Only positive integers less than Integer.MAX_VALUE are allowed: '" + s + "'");
		}
		try {
			final long n = Long.parseLong(s);
			assert n > 0 : "Not a positive integer: '" + s + "'. POSITIVE_INTEGER_REGEX is broken.";
			if (n > (long) Integer.MAX_VALUE) {
				return Either.left("The number is too big: " + s);
			}
			return Either.right((int) n);
		} catch (NumberFormatException e) {
			throw new AssertionError("Parsing is broken", e);
		}
	}

	public static <A> A extract(Either<A, A> e) {
		return e.match(Function.identity(), Function.identity());
	}

	public static void main(String... args) {
		String[] inputs = {
				"", "0", "-1", "1", "42", "foobar", "9 thousand",
				Integer.toString(Integer.MAX_VALUE),
				Long.toString(((long) Integer.MAX_VALUE) + 1L),
				"this string is too long"
		};
		for (String input : inputs) {
			Either<String, Integer> parsed = parsePositiveInteger(input);
			Either<String, String> formatted = EitherFunctor.fmap(
					(Integer i) -> i + " * " + i + " = " + (((long) i) * ((long) i)),
					parsed
			);
			System.out.println(extract(formatted));
		}
	}
}
