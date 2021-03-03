package net.fabricmc.mapping.util;

import java.util.Iterator;

final class UnmodifiableIterator<T> implements Iterator<T> {
	private final Iterator<T> iterator;

	UnmodifiableIterator(Iterator<T> iterator) {
		this.iterator = iterator;
	}

	@Override
	public boolean hasNext() {
		return this.iterator.hasNext();
	}

	@Override
	public T next() {
		return this.iterator.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
