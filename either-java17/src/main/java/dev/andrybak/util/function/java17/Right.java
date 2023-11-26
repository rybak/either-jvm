// SPDX-License-Identifier: MIT
package dev.andrybak.util.function.java17;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Right alternative of the {@link Either Either&lt;A, B&gt;} type, containing a value of type {@code B}.
 *
 * @param <A> type of the value in the corresponding {@link Left}
 * @param <B> type of the value in this {@link Right}
 * @param b   the {@code B} value stored in this {@link Left}
 */
public record Right<A, B>(B b) implements Either<A, B> {
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
}
