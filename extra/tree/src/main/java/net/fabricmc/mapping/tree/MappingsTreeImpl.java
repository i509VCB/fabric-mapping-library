package net.fabricmc.mapping.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.mapping.MappingsVisitor;

final class MappingsTreeImpl implements MappingsTree {
	private final List<String> namespaces = new ArrayList<>();

	@Override
	public List<String> getNamespaces() {
		return null;
	}

	@Override
	public Map<String, ClassDef> getDefaultNamespaceClassMap() {
		return null;
	}

	@Override
	public Collection<ClassDef> getClasses() {
		return null;
	}

	@Override
	public void accept(MappingsVisitor visitor) {

	}

	// Visitor impl

	@Override
	public void visitClass(NameGetter names) {

	}

	@Override
	public void visitMethod(NameGetter names, String descriptor) {

	}

	@Override
	public void visitField(NameGetter names, String descriptor) {

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
}
