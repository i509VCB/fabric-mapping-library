package net.fabricmc.mapping.util;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.mapping.MappingsVisitor;

/**
 * Contains useful mappings visitors for use with mappings.
 */
public final class MappingVisitors {
	/**
	 * Creates a mappings visitor which propagates visit calls to two other mappings visitors.
	 *
	 * @param first the first visitor
	 * @param second the second visitor
	 * @return a new mappings visitor
	 */
	public static MappingsVisitor dual(@Nullable MappingsVisitor first, @Nullable MappingsVisitor second) {
		return new DualVisitor(first, second);
	}

	private MappingVisitors() {
	}
}
