package net.fabricmc.mapping.lorenz;

import java.util.Objects;

import org.cadixdev.lorenz.MappingSet;
import org.cadixdev.lorenz.model.ClassMapping;
import org.cadixdev.lorenz.model.FieldMapping;
import org.cadixdev.lorenz.model.InnerClassMapping;
import org.cadixdev.lorenz.model.MethodMapping;
import org.cadixdev.lorenz.model.MethodParameterMapping;
import org.cadixdev.lorenz.model.TopLevelClassMapping;

import net.fabricmc.mapping.MappingsVisitor;
import net.fabricmc.mapping.tree.MappingsTree;

/**
 * Utilities for using fabric's mappings library with <a href="https://github.com/CadixDev/Lorenz">Lorenz</a>
 */
public final class LorenzMappings {
	public static void accept(MappingSet mappings, MappingsVisitor visitor) {
		Objects.requireNonNull(mappings, "MappingSet cannot be null");
		Objects.requireNonNull(visitor, "Visitor cannot be null");

		for (TopLevelClassMapping classMapping : mappings.getTopLevelClassMappings()) {
			acceptClasses(classMapping, visitor);
		}
	}

	public static MappingsTree toMappingTree(MappingSet mappings) {
		Objects.requireNonNull(mappings, "MappingSet cannot be null");

		final MappingsTree tree = MappingsTree.create();
		accept(mappings, tree);

		return tree;
	}

	private static void acceptClasses(ClassMapping<?, ?> classMapping, MappingsVisitor visitor) {
		acceptClass(classMapping, visitor);

		for (InnerClassMapping innerClassMapping : classMapping.getInnerClassMappings()) {
			acceptClasses(innerClassMapping, visitor);
		}
	}

	private static void acceptClass(ClassMapping<?, ?> mapping, MappingsVisitor visitor) {
		// TODO
		visitor.visitClass(new NameGetterImpl());

		for (FieldMapping fieldMapping : mapping.getFieldMappings()) {
			// TODO
			visitor.visitField(new NameGetterImpl(), fieldMapping.getType().map(Object::toString).orElse(null));
		}

		for (MethodMapping methodMapping : mapping.getMethodMappings()) {
			// TODO
			visitor.visitMethod(new NameGetterImpl(), methodMapping.getDescriptor().toString());

			for (MethodParameterMapping parameterMapping : methodMapping.getParameterMappings()) {
				// TODO
			}
		}
	}

	private LorenzMappings() {
	}

	// TODO
	private static final class NameGetterImpl implements MappingsVisitor.NameGetter {
		@Override
		public String get(int namespaceIndex) {
			return null;
		}

		@Override
		public String getRaw(int namespaceIndex) {
			return null;
		}

		@Override
		public String[] getRawNames() {
			return new String[0];
		}

		@Override
		public String[] getAllNames() {
			return new String[0];
		}
	}
}
