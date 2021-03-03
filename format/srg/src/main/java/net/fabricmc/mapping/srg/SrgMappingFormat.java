package net.fabricmc.mapping.srg;

import net.fabricmc.mapping.MappingFormat;
import net.fabricmc.mapping.MappingFormats;
import net.fabricmc.mapping.reader.ReadOptions;

public final class SrgMappingFormat implements MappingFormat {
	public static final MappingFormat FORMAT = MappingFormats.getFormat("srg");

	@Override
	public String getFormatName() {
		return "srg";
	}

	@Override
	public boolean supportsFeature(Feature feature) {
		return false;
	}

	@Override
	public ReadOptionSupport supportsReadOptions(Class<? extends ReadOptions> type) {
		// TODO: Srg specific settings?
		return ReadOptionSupport.NONE;
	}
}
