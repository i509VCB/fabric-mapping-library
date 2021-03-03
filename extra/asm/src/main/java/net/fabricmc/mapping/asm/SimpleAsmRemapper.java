package net.fabricmc.mapping.asm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.commons.Remapper;

import net.fabricmc.mapping.MappingsVisitor;

/**
 * A simple {@link Remapper ASM Remapper} which may be used to remap class, field and method names between two namespaces.
 * To populate the mappings for in the remapper, simply use pass the remapper as a {@link MappingsVisitor visitor}.
 *
 * @see Remapper
 */
public class SimpleAsmRemapper extends Remapper implements MappingsVisitor {
	private final Map<String, String> classNames = new HashMap<>();
	private final Map<MemberEntry, String> fieldNames = new HashMap<>();
	private final Map<MemberEntry, String> methodNames = new HashMap<>();
	private final String fromNamespace;
	private final String toNamespace;
	@Nullable
	private String lastVisitedClassFromName = null;
	private int toNamespaceIndex = -1;
	private int fromNamespaceIndex = -1;
	private boolean namespacesVisited;

	public SimpleAsmRemapper(String fromNamespace, String toNamespace) {
		this.fromNamespace = fromNamespace;
		this.toNamespace = toNamespace;
	}

	@Override
	public String map(String internalName) {
		return this.classNames.getOrDefault(internalName, internalName);
	}

	@Override
	public String mapFieldName(String owner, String name, String descriptor) {
		return this.fieldNames.getOrDefault(new MemberEntry(owner, name, descriptor), name);
	}

	@Override
	public String mapMethodName(String owner, String name, String descriptor) {
		return this.methodNames.getOrDefault(new MemberEntry(owner, name, descriptor), name);
	}

	// Visitor implementation

	@Override
	public void visitClass(NameGetter names) {
		if (!this.namespacesVisited) {
			if (names.getRawNames().length != 2) {
				throw new IllegalStateException(""); // TODO:
			}

			this.toNamespaceIndex = 0;
			this.fromNamespaceIndex = 1;
		}

		this.lastVisitedClassFromName = names.get(this.fromNamespaceIndex);
		this.classNames.put(this.lastVisitedClassFromName, names.get(this.toNamespaceIndex));
	}

	@Override
	public void visitMethod(NameGetter names, String descriptor) {
		if (this.lastVisitedClassFromName == null) {
			throw new IllegalStateException("Cannot visit method outside of a class!");
		}

		this.methodNames.put(new MemberEntry(this.lastVisitedClassFromName, names.get(this.fromNamespaceIndex), descriptor), names.get(this.toNamespaceIndex));
	}

	@Override
	public void visitField(NameGetter names, String descriptor) {
		if (this.lastVisitedClassFromName == null) {
			throw new IllegalStateException("Cannot visit field outside of a class!");
		}

		this.fieldNames.put(new MemberEntry(this.lastVisitedClassFromName, names.get(this.fromNamespaceIndex), descriptor), names.get(this.toNamespaceIndex));
	}

	@Override
	public void visitClassComment(String comment) {
	}

	@Override
	public void visitFieldComment(String comment) {
	}

	@Override
	public void visitMethodComment(String comment) {
	}

	@Override
	public void visitMethodParameterComment(String comment) {
	}

	@Override
	public void visitNamespaces(List<String> namespaces) {
		if (this.namespacesVisited) {
			throw new RuntimeException();
		}

		this.namespacesVisited = true;

		if (!namespaces.contains(this.toNamespace)) {
			throw new RuntimeException();
		}

		if (!namespaces.contains(this.fromNamespace)) {
			throw new RuntimeException();
		}

		this.fromNamespaceIndex = namespaces.indexOf(this.fromNamespace);
		this.toNamespaceIndex = namespaces.indexOf(this.toNamespace);
	}

	@Override
	public void visitMethodParameter(NameGetter names, int localVariableIndex) {
	}

	@Override
	public void visitMethodLocalVariable(NameGetter names, int localVariableIndex, int localVariableStartOffset, int localVariableTableIndex) {
	}

	@Override
	public void visitMethodLocalVariableComment(String comment) {
	}

	@Override
	public void visitProperty(String key, @Nullable String value) {
	}

	@Override
	public void visitEnd() {
	}

	private static final class MemberEntry {
		private final String owner;
		private final String name;
		private final String descriptor;

		public MemberEntry(String owner, String name, String descriptor) {
			this.owner = owner;
			this.name = name;
			this.descriptor = descriptor;
		}

		@Override
		public String toString() {
			return "MemberEntry{owner=" + this.owner + ",name=" + this.name + ",desc=" + this.descriptor + "}";
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof MemberEntry)) {
				return false;
			} else if (o == this) {
				return true;
			} else {
				MemberEntry other = (MemberEntry) o;

				return other.owner.equals(this.owner) && other.name.equals(this.name) && other.descriptor.equals(this.descriptor);
			}
		}

		@Override
		public int hashCode() {
			return this.owner.hashCode() * 37 + this.name.hashCode() * 19 + this.descriptor.hashCode();
		}
	}
}
