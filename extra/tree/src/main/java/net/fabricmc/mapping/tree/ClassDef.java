package net.fabricmc.mapping.tree;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.mapping.MappingsVisitor;

public interface ClassDef {
	Collection<MethodDef> getMethods();

	Collection<FieldDef> getFields();

	@Nullable
	String getComment();
}
