package net.fabricmc.mapping.proguard;

import net.fabricmc.mapping.MappingFormat;
import net.fabricmc.mapping.MappingFormats;
import net.fabricmc.mapping.reader.ReadOptions;

public final class ProguardMappingFormat implements MappingFormat {
	public static final MappingFormat FORMAT = MappingFormats.getFormat("proguard");

	@Override
	public String getFormatName() {
		return "proguard";
	}

	@Override
	public boolean supportsFeature(Feature feature) {
		return false;
	}

	@Override
	public ReadOptionSupport supportsReadOptions(Class<? extends ReadOptions> type) {
		return ReadOptionSupport.NONE; // TODO: Impl
	}
}
