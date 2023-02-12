// SPDX-License-Identifier: MIT
package dev.andrybak.util.function.samples;

import dev.andrybak.util.function.java8.Either;

import java.io.IOException;

public class EitherUtils {
	private EitherUtils() {
		throw new AssertionError();
	}

	public static <B> Either<Exception, B> wrapSupplier(SupplierCanThrow<B> supplier) {
		try {
			return Either.right(supplier.get());
		} catch (Exception e) {
			return Either.left(e);
		}
	}

	public static int foo() throws IOException {
		return 42;
	}

	public static void main(String[] args) {
		printResult(() -> Integer.parseInt(args[0]));
		printResult(() -> Integer.parseInt("foobar"));
		printResult(EitherUtils::foo);
	}

	private static void printResult(SupplierCanThrow<Integer> s) {
		Either<Exception, Integer> res = wrapSupplier(s);
		res.match(a -> {
			System.out.println("Got exception: " + a);
			return null;
		}, b -> {
			System.out.println("Got number: " + b);
			return null;
		});
	}

	@FunctionalInterface
	public interface SupplierCanThrow<B> {
		B get() throws Exception;
	}
}
