package net.fabricmc.mapping.proguard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import net.fabricmc.mapping.MappingsVisitor;
import net.fabricmc.mapping.ParseException;
import net.fabricmc.mapping.reader.MappingReader;

// Based on specification here:
// https://www.guardsquare.com/en/products/proguard/manual/retrace
final class ProguardReader implements MappingReader {
	private final Reader reader;

	ProguardReader(Reader reader) {
		this.reader = reader;
	}

	@Override
	public void read(MappingsVisitor visitor) throws IOException, ParseException {
		BufferedReader reader;

		if (this.reader instanceof BufferedReader) {
			reader = (BufferedReader) this.reader;
		} else {
			reader = new BufferedReader(this.reader);
		}

		boolean inClass = false;

		try (final BufferedReader bufferedReader = reader) {
			String line;
			String[] parts;

			while ((line = bufferedReader.readLine()) != null) {
				if (line.isEmpty()) continue;
				if (line.startsWith("#")) continue;

				// TODO: Impl
			}
		}
	}

	@Override
	public void close() throws Exception {
		this.reader.close();
	}

	private static final class NameGetterImpl implements MappingsVisitor.NameGetter {
		private final String obfuscated;
		private final String deobfuscated;

		NameGetterImpl(String obfuscated, String deobfuscated) {
			this.obfuscated = obfuscated;
			this.deobfuscated = deobfuscated;
		}

		@Override
		public String get(int namespaceIndex) {
			return null;
		}

		@Override
		public String getRaw(int namespaceIndex) {
			return null;
		}

		@Override
		public String[] getRawNames() {
			return new String[0];
		}

		@Override
		public String[] getAllNames() {
			return new String[0];
		}
	}
}
