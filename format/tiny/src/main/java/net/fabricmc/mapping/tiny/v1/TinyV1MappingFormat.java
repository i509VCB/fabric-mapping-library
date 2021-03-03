package net.fabricmc.mapping.tiny.v1;

import java.io.Reader;
import java.io.Writer;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import net.fabricmc.mapping.MappingFormat;
import net.fabricmc.mapping.MappingFormats;
import net.fabricmc.mapping.MappingWriter;
import net.fabricmc.mapping.reader.BasicReadOptions;
import net.fabricmc.mapping.reader.MappingReader;
import net.fabricmc.mapping.reader.ReadOptions;

public final class TinyV1MappingFormat implements MappingFormat {
	public static final MappingFormat FORMAT = MappingFormats.getFormat("tinyv1");
	static final Set<Feature> FEATURES = Collections.unmodifiableSet(EnumSet.of(Feature.COMMENTS, Feature.NAMED_NAMESPACES));

	@Override
	public String getFormatName() {
		return "tinyv1";
	}

	@Override
	public boolean supportsFeature(Feature feature) {
		return FEATURES.contains(feature);
	}

	@Override
	public ReadOptionSupport supportsReadOptions(Class<? extends ReadOptions> type) {
		// TODO:
		return ReadOptionSupport.NONE;
	}

	@Override
	public boolean supportsReading() {
		return true;
	}

	@Override
	public boolean supportsWriting() {
		return true;
	}

	@Override
	public MappingReader createReader(Reader reader) {
		return null;
	}

	@Override
	public MappingWriter createWriter(Writer writer) {
		return null;
	}
}
