// SPDX-License-Identifier: MIT
package dev.andrybak.util.function.java17;

import java.util.function.Function;

public final class Right<A, B> extends Either<A, B> {
	private final B b;

	Right(B b) {
		this.b = b;
	}

	@Override
	public <R> R match(Function<? super A, ? extends R> f, Function<? super B, ? extends R> g) {
		return g.apply(b);
	}
}
