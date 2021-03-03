package net.fabricmc.mapping;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.mapping.annotation.Requires;

/**
 * A visitor used to visit a mappings.
 *
 * Below are diagrams showing how visitor implementations are expected to be called.
 * All methods marked as {@code Optional} are not required to be called.
 * The {@code |} symbol specifies that two or more visitor methods may be called in any order.
 *
 * <pre>{@code
 * visitNamespaces (Optional)
 * visitProperty (Optional)
 * visitContent
 * visitEnd
 * }</pre>
 *
 * When visiting the content of a mappings file, the visitor methods are called as follows:
 * <pre>{@code
 * visitContent {
 * 	visitClass
 * 		visitMethod | visitField | visitClassComment (Optional)
 * }
 * }</pre>
 *
 * When visiting a method mapping, the visitor methods are called as follows:
 * <pre>{@code
 * visitMethod {
 * 	visitMethodParameter (Optional) | visitMethodParameterComment (Optional) | visitMethodComment (Optional) | visitMethodLocalVariable (Optional)
 * }
 *
 * When visiting a field mapping, the visitor methods are called as follows:
 * visitField {
 * 	visitFieldComment (Optional)
 * }
 *
 * When visiting the end, there will be no more mappings to explore.
 * }</pre>
 */
public interface MappingsVisitor {
	/**
	 * Visits mappings of a class name.
	 *
	 * <p>If {@link MappingsVisitor#visitNamespaces(List)} has been called, the index of each namespace on the list corresponds to the index of the passed array of class names.
	 *
	 * <p>If {@link MappingsVisitor#visitNamespaces(List)} was never called, the array length shall be 2 and
	 * {@code classNames[0]} is the {@link MappingFormat#OBFUSCATED_NAMESPACE obfuscated} namespace and
	 * {@code classNames[1]} is the {@link MappingFormat#DEOBFUSCATED_NAMESPACE deobfuscated} namespace.
	 *
	 * @param names
	 */
	void visitClass(NameGetter names);

	/**
	 * Visits mappings of a method.
	 *
	 * <p>It is expected that the method in this
	 *
	 * @param names
	 * @param descriptor
	 */
	void visitMethod(NameGetter names, String descriptor);

	void visitField(NameGetter names, @Requires(MappingFormat.Feature.FIELD_SIGNATURES) String descriptor);

	// Requires MappingFormat.Feature.COMMENTS to be supported

	@Requires(MappingFormat.Feature.COMMENTS)
	void visitClassComment(String comment);

	@Requires(MappingFormat.Feature.COMMENTS)
	void visitFieldComment(String comment);

	@Requires(MappingFormat.Feature.COMMENTS)
	void visitMethodComment(String comment);

	@Requires(MappingFormat.Feature.COMMENTS)
	void visitMethodParameterComment(String comment);

	// Requires MappingFormat.Feature.NAMED_NAMESPACES to be supported

	/**
	 * Visits a list of all supported namespaces.
	 *
	 * @param namespaces an immutable list of namespaces
	 */
	@Requires(MappingFormat.Feature.NAMED_NAMESPACES)
	void visitNamespaces(List<String> namespaces);

	// Requires MappingFormat.Feature.METHOD_PARAMETERS to be supported

	@Requires(MappingFormat.Feature.METHOD_PARAMETERS)
	void visitMethodParameter(NameGetter names, int localVariableIndex);

	// Requires MappingFormat.Feature.METHOD_LOCAL_VARIABLES to be supported

	@Requires(MappingFormat.Feature.METHOD_LOCAL_VARIABLES)
	void visitMethodLocalVariable(NameGetter names, int localVariableIndex, int localVariableStartOffset, int localVariableTableIndex);

	// Requires MappingFormat.Feature.METHOD_LOCAL_VARIABLES and MappingFormat.Feature.COMMENTS to be supported
	@Requires({MappingFormat.Feature.COMMENTS, MappingFormat.Feature.METHOD_LOCAL_VARIABLES})
	void visitMethodLocalVariableComment(String comment);

	// Requires MappingFormat.Feature.PROPERTIES to be supported

	@Requires(MappingFormat.Feature.PROPERTIES)
	void visitProperty(String key, @Nullable String value);

	void visitEnd();

	interface NameGetter {
		String get(int namespaceIndex);

		String getRaw(int namespaceIndex);

		String[] getRawNames();

		String[] getAllNames();
	}
}
