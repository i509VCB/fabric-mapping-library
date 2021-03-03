package net.fabricmc.mapping.tiny.v1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.fabricmc.mapping.MappingsVisitor;
import net.fabricmc.mapping.ParseException;
import net.fabricmc.mapping.reader.MappingReader;

final class TinyV1Reader implements MappingReader {
	private final Reader reader;

	TinyV1Reader(Reader reader) {
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

		try (final BufferedReader bufferedReader = reader) {
			final String firstLine = bufferedReader.readLine();
			String[] parts;

			if (!firstLine.startsWith("v1\t")) {
				throw new ParseException("invalid/unsupported tiny file (incorrect header)");
			}

			parts = firstLine.split("\t");
			final List<String> namespaces = Collections.unmodifiableList(Arrays.asList(Arrays.copyOfRange(parts, 1, parts.length)));
			final int namespaceCount = namespaces.size();

			visitor.visitNamespaces(namespaces);

			final int partCountHint = 1 + namespaceCount;

			int lineNumber = 1;
			boolean inClass = false;

			String line;

			while ((line = bufferedReader.readLine()) != null) {
				lineNumber++;
				if (line.isEmpty()) continue;

				parts = line.split("\t");

				if (parts.length < 2) {
					throw new ParseException(""); // TODO: MSG
				}

				switch (parts[0]) {
				case "CLASS":
					inClass = true;



					break;
				case "METHOD":
					if (!inClass) {
						throw new ParseException(""); // TODO: MSG
					}

					visitor.visitMethod(new NameGetterImpl(3, namespaceCount, parts), parts[2]);

					break;
				case "FIELD":
					if (!inClass) {
						throw new ParseException(""); // TODO: MSG
					}

					break;
				default:

				}
			}
		}
	}

	@Override
	public void close() throws Exception {
		this.reader.close();
	}

	private static final class NameGetterImpl implements MappingsVisitor.NameGetter {
		NameGetterImpl(int offset, int namespaceCount, String[] parts) {

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
