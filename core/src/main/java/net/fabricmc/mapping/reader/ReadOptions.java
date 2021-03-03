package net.fabricmc.mapping.reader;

/**
 * Holds specific settings that should be considered while reading mappings.
 *
 * Most mapping formats will typically accept {@link BasicReadOptions}.
 * Some mapping formats may provide their own implementation of read options for implementation specific logic.
 * The mapping format will specify the preferred read option type in the respective javadoc.
 */
public interface ReadOptions {
}
