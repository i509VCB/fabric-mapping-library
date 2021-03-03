package net.fabricmc.mapping.tree;

import org.jetbrains.annotations.Nullable;

public interface ParameterDef {
	String getName();

	@Nullable
	String getComment();

	int getLocalVariableIndex();
}
