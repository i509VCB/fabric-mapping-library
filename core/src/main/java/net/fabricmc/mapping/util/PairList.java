package net.fabricmc.mapping.util;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

final class PairList<T> extends AbstractList<T> {
	private final T first;
	private final T second;

	public PairList(T first, T second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public T get(int index) {
		switch (index) {
		case 0:
			return this.first;
		case 1:
			return this.second;
		default:
			throw new IndexOutOfBoundsException(); // TODO: Message
		}
	}

	@Override
	public int size() {
		return 2;
	}

	@Override
	public void forEach(Consumer<? super T> action) {
		Objects.requireNonNull(action);

		action.accept(this.first);
		action.accept(this.second);
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private int pos = 0;

			@Override
			public boolean hasNext() {
				return this.pos != 2;
			}

			@Override
			public T next() {
				if (this.hasNext()) {
					final T element = PairList.this.get(this.pos);
					this.pos++;
					return element;
				}

				throw new NoSuchElementException();
			}
		};
	}

	@Override
	public Stream<T> stream() {
		return Stream.of(this.first, this.second);
	}
}
