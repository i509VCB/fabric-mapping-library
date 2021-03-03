package net.fabricmc.mapping.reader;

import java.io.IOException;

import net.fabricmc.mapping.MappingsVisitor;
import net.fabricmc.mapping.ParseException;

/**
 * A reader used to explore mappings using a supplied visitor.
 * Mapping readers are typically single use.
 */
public interface MappingReader extends AutoCloseable {
	/**
	 * Explores a mapping file, using the supplied visitor.
	 *
	 * @param visitor the visitor to visit the mapping file with
	 * @throws IOException if there are issues while reading the mappings
	 * @throws ParseException if there is any malformed syntax in the mappings
	 */
	void read(MappingsVisitor visitor) throws IOException, ParseException;
}
