package net.fabricmc.mapping.tree;

import java.util.Collection;

import org.jetbrains.annotations.Nullable;

public interface MethodDef {
	String getDescriptor(String namespace);

	@Nullable
	String getComment();

	Collection<ParameterDef> getParameters();

	Collection<LocalVariableDef> getLocalVariables();
}
