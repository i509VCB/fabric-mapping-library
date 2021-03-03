package net.fabricmc.mapping.util;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

/**
 * Extra utilities for collections.
 */
public final class Collections2 {
	/**
	 * Creates a list with two elements.
	 *
	 * @param first the first element
	 * @param second the second element
	 * @param <T> the type of element stored in the list
	 * @return a new list
	 */
	@Unmodifiable
	public static <T> List<T> pairList(T first, T second) {
		return new PairList<>(first, second);
	}

	@UnmodifiableView
	public static <T> Iterator<T> unmodifiableIterator(Iterator<T> iterator) {
		Objects.requireNonNull(iterator, "Backing iterator cannot be null");
		return new UnmodifiableIterator<>(iterator);
	}

	private Collections2() {
	}
}

