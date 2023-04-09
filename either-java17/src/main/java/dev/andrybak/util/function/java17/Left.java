// SPDX-License-Identifier: MIT
package dev.andrybak.util.function.java17;

import java.util.Objects;
import java.util.function.Consumer;
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

	@Override
	public void accept(Consumer<? super A> f, Consumer<? super B> g) {
		f.accept(a);
	}

	@Override
	public Either<A, B> peek(Consumer<? super A> f, Consumer<? super B> g) {
		f.accept(a);
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Left<?, ?> left = (Left<?, ?>) o;

		return Objects.equals(a, left.a);
	}

	@Override
	public int hashCode() {
		return a != null ? (a.hashCode() * 31) : 0;
	}

	@Override
	public String toString() {
		return "Left(" + a + ')';
	}
}
