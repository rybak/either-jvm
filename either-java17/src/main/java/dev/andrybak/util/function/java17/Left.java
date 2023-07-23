// SPDX-License-Identifier: MIT
package dev.andrybak.util.function.java17;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Left alternative of the {@link Either Either&lt;A, B&gt;} type, containing a value of type {@code A}.
 *
 * @param <A> type of the value in this {@code Left}
 * @param <B> type of the value in the corresponding {@link Right}
 */
public record Left<A, B>(A a) implements Either<A, B> {
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
}
