package net.fabricmc.mapping.tree;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.fabricmc.mapping.MappingsVisitor;

public interface MappingsTree extends MappingsVisitor {
	static MappingsTree create() {
		return new MappingsTreeImpl();
	}

	List<String> getNamespaces();

	Map<String, ClassDef> getDefaultNamespaceClassMap();

	Collection<ClassDef> getClasses();

	void accept(MappingsVisitor visitor);
}
