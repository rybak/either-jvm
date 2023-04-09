// SPDX-License-Identifier: MIT
package dev.andrybak.util.function.java17;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Right alternative of the {@link Either Either&lt;A, B&gt;} type, containing a value of type {@code B}.
 *
 * @param <A> type of the value in the corresponding {@link Left}
 * @param <B> type of the value in this {@code Right}
 */
public final class Right<A, B> extends Either<A, B> {
	/**
	 * The value of this {@link Right}.
	 */
	private final B b;

	Right(B b) {
		this.b = b;
	}

	@Override
	public <R> R match(Function<? super A, ? extends R> f, Function<? super B, ? extends R> g) {
		return g.apply(b);
	}

	@Override
	public void accept(Consumer<? super A> f, Consumer<? super B> g) {
		g.accept(b);
	}

	@Override
	public Either<A, B> peek(Consumer<? super A> f, Consumer<? super B> g) {
		g.accept(b);
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Right<?, ?> right = (Right<?, ?>) o;

		return Objects.equals(b, right.b);
	}

	@Override
	public int hashCode() {
		return b != null ? (b.hashCode() * 37) : 0;
	}

	@Override
	public String toString() {
		return "Right(" + b + ')';
	}
}
