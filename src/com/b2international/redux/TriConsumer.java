package com.b2international.redux;

import java.util.Objects;

@FunctionalInterface
public interface TriConsumer<T, U, V> {
	
	void accept(T t, U u, V v);
	
	default TriConsumer<T, U, V> andThen(TriConsumer<T, U, V> after) {
		Objects.requireNonNull(after);
		
		return (l, r, v) -> {
			accept(l, r, v);
			after.accept(l, r, v);
		};
	}

}
