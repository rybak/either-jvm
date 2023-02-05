// SPDX-License-Identifier: MIT
package dev.andrybak.util.function.java17;

import java.util.function.Function;

/**
 * Left alternative of the {@link Either Either&lt;A, B&gt;} type, containing a value of type {@code A}.
 *
 * @param <A> type of the value in this {@code Left}
 * @param <B> type of the value in the corresponding {@link Right}
 */
public final class Left<A, B> extends Either<A, B> {
	/**
	 * The value of this {@link Left}.
	 */
	private final A a;

	Left(A a) {
		this.a = a;
	}

	@Override
	public <R> R match(Function<? super A, ? extends R> f, Function<? super B, ? extends R> g) {
		return f.apply(a);
	}
}
