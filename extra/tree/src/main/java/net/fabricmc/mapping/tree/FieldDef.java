package net.fabricmc.mapping.tree;

import org.jetbrains.annotations.Nullable;

public interface FieldDef {
	@Nullable
	String getDescriptor(String namespace);

	boolean hasDescriptor();

	@Nullable
	String getComment();
}
