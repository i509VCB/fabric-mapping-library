package net.fabricmc.mapping.tree;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

public interface LocalVariableDef {
	String getName();

	@Nullable
	String getComment();

	@Range(from = 0, to = Integer.MAX_VALUE)
	int getLocalVariableIndex();

	@Range(from = 0, to = Integer.MAX_VALUE)
	int getLocalVariableStartOffset();

	int getLocalVariableTableIndex();
}
