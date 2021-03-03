package net.fabricmc.mapping;

import java.io.IOException;
import java.io.Writer;
import java.util.function.Consumer;

public interface MappingWriter {
	/**
	 *
	 * @param writer
	 * @param mappings
	 */
	void write(Writer writer, Consumer<MappingsVisitor> mappings) throws IOException;
}
