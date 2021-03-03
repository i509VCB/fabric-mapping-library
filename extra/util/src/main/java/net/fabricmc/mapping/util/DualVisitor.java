package net.fabricmc.mapping.util;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.mapping.MappingsVisitor;

final class DualVisitor implements MappingsVisitor {
	private final MappingsVisitor first;
	private final MappingsVisitor second;

	DualVisitor(MappingsVisitor first, MappingsVisitor second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public void visitClass(NameGetter names) {
		if (this.first != null) this.first.visitClass(names);
		if (this.second != null) this.second.visitClass(names);
	}

	@Override
	public void visitMethod(NameGetter names, String descriptor) {
		if (this.first != null) this.first.visitMethod(names, descriptor);
		if (this.second != null) this.second.visitMethod(names, descriptor);
	}

	@Override
	public void visitField(NameGetter names, String descriptor) {
		if (this.first != null) this.first.visitField(names, descriptor);
		if (this.second != null) this.second.visitField(names, descriptor);
	}

	@Override
	public void visitClassComment(String comment) {
		if (this.first != null) this.first.visitClassComment(comment);
		if (this.second != null) this.second.visitClassComment(comment);
	}

	@Override
	public void visitFieldComment(String comment) {
		if (this.first != null) this.first.visitFieldComment(comment);
		if (this.second != null) this.second.visitFieldComment(comment);
	}

	@Override
	public void visitMethodComment(String comment) {
		if (this.first != null) this.first.visitMethodComment(comment);
		if (this.second != null) this.second.visitMethodComment(comment);
	}

	@Override
	public void visitMethodParameterComment(String comment) {
		if (this.first != null) this.first.visitMethodParameterComment(comment);
		if (this.second != null) this.second.visitMethodParameterComment(comment);
	}

	@Override
	public void visitNamespaces(List<String> namespaces) {
		if (this.first != null) this.first.visitNamespaces(namespaces);
		if (this.second != null) this.second.visitNamespaces(namespaces);
	}

	@Override
	public void visitMethodParameter(NameGetter names, int localVariableIndex) {
		if (this.first != null) this.first.visitMethodParameter(names, localVariableIndex);
		if (this.second != null) this.second.visitMethodParameter(names, localVariableIndex);
	}

	@Override
	public void visitMethodLocalVariable(NameGetter names, int localVariableIndex, int localVariableStartOffset, int localVariableTableIndex) {
		if (this.first != null) this.first.visitMethodLocalVariable(names, localVariableIndex, localVariableStartOffset, localVariableTableIndex);
		if (this.second != null) this.second.visitMethodLocalVariable(names, localVariableIndex, localVariableStartOffset, localVariableTableIndex);
	}

	@Override
	public void visitMethodLocalVariableComment(String comment) {
		if (this.first != null) this.first.visitMethodLocalVariableComment(comment);
		if (this.second != null) this.second.visitMethodLocalVariableComment(comment);
	}

	@Override
	public void visitProperty(String key, @Nullable String value) {
		if (this.first != null) this.first.visitProperty(key, value);
		if (this.second != null) this.second.visitProperty(key, value);
	}

	@Override
	public void visitEnd() {
		if (this.first != null) this.first.visitEnd();
		if (this.second != null) this.second.visitEnd();
	}
}
