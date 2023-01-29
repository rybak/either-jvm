// SPDX-License-Identifier: MIT
package dev.andrybak.util.function.java17;

import java.util.function.Function;

public final class Left<A, B> extends Either<A, B> {
	private final A a;

	Left(A a) {
		this.a = a;
	}

	@Override
	public <R> R match(Function<? super A, R> f, Function<? super B, R> g) {
		return f.apply(a);
	}
}
