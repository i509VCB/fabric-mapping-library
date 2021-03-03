package net.fabricmc.mapping.tiny.v2;

import java.io.Reader;
import java.io.Writer;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import net.fabricmc.mapping.MappingFormat;
import net.fabricmc.mapping.MappingFormats;
import net.fabricmc.mapping.MappingWriter;
import net.fabricmc.mapping.reader.MappingReader;
import net.fabricmc.mapping.reader.ReadOptions;

public final class TinyV2MappingFormat implements MappingFormat {
	public static final MappingFormat FORMAT = MappingFormats.getFormat("tinyv2");
	static final Set<Feature> FEATURES = Collections.unmodifiableSet(EnumSet.of(Feature.COMMENTS, Feature.NAMED_NAMESPACES, Feature.METHOD_PARAMETERS, Feature.METHOD_LOCAL_VARIABLES));

	@Override
	public String getFormatName() {
		return "tinyv2";
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
		return new TinyV2Reader(reader);
	}

	@Override
	public MappingReader createReader(Reader reader, ReadOptions readOptions) {
		return new TinyV2Reader(reader);
	}

	@Override
	public MappingWriter createWriter(Writer writer) {
		return new TinyV2Writer();
	}
}
