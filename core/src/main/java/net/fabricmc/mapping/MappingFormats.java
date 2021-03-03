package net.fabricmc.mapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public final class MappingFormats {
	private static final Map<String, MappingFormat> MAPPING_FORMATS;

	public static MappingFormat getFormat(String name) throws IllegalArgumentException {
		final MappingFormat format = MAPPING_FORMATS.get(name);

		if (format != null) {
			return format;
		}

		throw new IllegalArgumentException();
	}

	static {
		final Map<String, MappingFormat> formats = new HashMap<>();
		final ServiceLoader<MappingFormat> loader = ServiceLoader.load(MappingFormat.class);

		for (MappingFormat format : loader) {
			if (formats.putIfAbsent(format.getFormatName(), format) != null) {
				throw new RuntimeException(); // TODO
			}
		}

		MAPPING_FORMATS = Collections.unmodifiableMap(formats);
	}

	private MappingFormats() {
	}
}
