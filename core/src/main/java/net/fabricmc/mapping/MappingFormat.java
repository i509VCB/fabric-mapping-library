package net.fabricmc.mapping;

import java.io.Reader;
import java.io.Writer;

import net.fabricmc.mapping.reader.MappingReader;
import net.fabricmc.mapping.reader.ReadOptions;

public interface MappingFormat {
	String OBFUSCATED_NAMESPACE = "obfuscated";
	String DEOBFUSCATED_NAMESPACE = "deobfuscated";

	String getFormatName();

	boolean supportsFeature(Feature feature);

	ReadOptionSupport supportsReadOptions(Class<? extends ReadOptions> type);

	default boolean supportsReading() {
		return false;
	}

	default boolean supportsWriting() {
		return false;
	}

	default MappingReader createReader(Reader reader) {
		throw new UnsupportedOperationException("Format does not support reading");
	}

	default MappingReader createReader(Reader reader, ReadOptions readOptions) {
		throw new UnsupportedOperationException("Format does not support reading");
	}

	default MappingWriter createWriter(Writer writer) {
		throw new UnsupportedOperationException("Format does not support writing");
	}

	enum Feature {
		/**
		 * Specifies that a mapping format supports comments on classes, fields, method and method parameters.
		 */
		COMMENTS,
		/**
		 * Specifies that a mapping format supports field signatures.
		 */
		FIELD_SIGNATURES,
		/**
		 * Specifies that a mapping format supports named namespaces.
		 *
		 * <p>If the mapping format does not support named namespaces, it is assumed the two namespaces are {@link MappingFormat#OBFUSCATED_NAMESPACE obfuscated} and {@link MappingFormat#DEOBFUSCATED_NAMESPACE deobfuscated}.
		 */
		NAMED_NAMESPACES,
		/**
		 * Specifies that a mapping format supports named method parameters.
		 */
		METHOD_PARAMETERS,
		/**
		 * Specifies that a mapping format supports naming of local variables in methods.
		 */
		METHOD_LOCAL_VARIABLES,
		/**
		 * Specifies that a mapping format supports specification of uniquely keyed additional properties.
		 */
		PROPERTIES;
	}

	enum ReadOptionSupport {
		NONE,
		PARTIAL,
		COMPLETE
	}
}
