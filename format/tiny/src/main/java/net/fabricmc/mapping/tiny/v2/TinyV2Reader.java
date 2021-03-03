package net.fabricmc.mapping.tiny.v2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.fabricmc.mapping.reader.MappingReader;
import net.fabricmc.mapping.MappingsVisitor;
import net.fabricmc.mapping.ParseException;

final class TinyV2Reader implements MappingReader {
	private static final String TO_ESCAPE = "\\\n\r\0\t";
	private static final String ESCAPED = "\\nr0t";
	private final Reader reader;

	TinyV2Reader(Reader reader) {
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

			//min. tiny + major version + minor version + 2 name spaces
			if (firstLine == null || !firstLine.startsWith("tiny\t2\t") || (parts = splitAtTab(firstLine, 0, 5)).length < 5) {
				throw new ParseException("invalid/unsupported tiny file (incorrect header)");
			}

			// Always supply a read only copy for visitors
			final List<String> namespaces = Collections.unmodifiableList(Arrays.asList(Arrays.copyOfRange(parts, 3, parts.length)));
			final int namespaceCount = namespaces.size();

			visitor.visitNamespaces(namespaces);

			final int partCountHint = 2 + namespaceCount; // suitable for members, which should be the majority

			int lineNumber = 1;

			boolean inHeader = true;
			boolean inClass = false;
			boolean inMethod = false;
			boolean inField = false;
			boolean inMethodParam = false;
			boolean inMethodVar = false;

			// Builtin property
			boolean escapedNames = false;

			String line;

			while ((line = bufferedReader.readLine()) != null) {
				lineNumber++;
				if (line.isEmpty()) continue;

				int indent = 0;

				while (indent < line.length() && line.charAt(indent) == '\t') {
					indent++;
				}

				parts = splitAtTab(line, indent, partCountHint);
				String section = parts[0];

				if (indent == 0) {
					inHeader = inClass = inMethod = inField = inMethodParam = inMethodVar = false;

					// class: c <names>...
					if (section.equals("c")) {
						if (parts.length != namespaceCount + 1) {
							throw new ParseException("invalid class declaration in line " + lineNumber);
						}

						visitor.visitClass(new NameGetterImpl(1, namespaceCount, parts, escapedNames));
						inClass = true;
					}
				} else if (indent == 1) {
					inMethod = inField = inMethodParam = inMethodVar = false;

					if (inHeader) { // header k/v
						if (section.equals("escaped-names")) {
							escapedNames = true;
						}

						// A property may have a defined value or will have a null value to show just presence of a key
						visitor.visitProperty(section, parts.length == 3 ? parts[2] : null);
					// method/field: m/f <descA> <names>...
					} else if (inClass && (section.equals("m") || section.equals("f"))) {
						boolean isMethod = section.equals("m");

						if (parts.length != namespaceCount + 2) {
							throw new ParseException("invalid " + (isMethod ? "method" : "field") + " decl in line " + lineNumber);
						}

						final String memberDesc = unescapeOpt(parts[1], escapedNames);

						if (isMethod) {
							visitor.visitMethod(new NameGetterImpl(2, namespaceCount, parts, escapedNames), memberDesc);
						} else {
							visitor.visitField(new NameGetterImpl(2, namespaceCount, parts, escapedNames), memberDesc);
						}

						if (isMethod) {
							inMethod = true;
						} else {
							inField = true;
						}
					// class comment: c <comment>
					} else if (inClass && section.equals("c")) {
						if (parts.length != 2) {
							throw new ParseException("invalid class comment in line " + lineNumber);
						}

						visitor.visitClassComment(unescape(parts[1]));
					} else {
						// TODO: Check read options and warn about unknown keys?
					}
				} else if (indent == 2) {
					inMethodParam = inMethodVar = false;

					// method parameter: p <lv-index> <names>...
					if (inMethod && section.equals("p")) {
						if (parts.length != namespaceCount + 2) {
							throw new ParseException("invalid method parameter decl in line " + lineNumber);
						}

						int varLvIndex = Integer.parseInt(parts[1]);

						visitor.visitMethodParameter(new NameGetterImpl(3, namespaceCount, parts, escapedNames), varLvIndex);
						inMethodParam = true;
					// method variable: v <lv-index> <lv-start-offset> <optional-lvt-index> <names>...
					} else if (inMethod && section.equals("v")) {
						if (parts.length != namespaceCount + 4) {
							throw new ParseException("invalid method variable decl in line " + lineNumber);
						}

						int varLvIndex = Integer.parseInt(parts[1]);
						int varStartOpIdx = Integer.parseInt(parts[2]);
						int varLvtIndex = Integer.parseInt(parts[3]);

						visitor.visitMethodLocalVariable(new NameGetterImpl(3, namespaceCount, parts, escapedNames), varLvIndex, varStartOpIdx, varLvtIndex);
						inMethodVar = true;
					// method/field comment: c <comment>
					} else if ((inMethod || inField) && section.equals("c")) {
						if (parts.length != 2) {
							throw new ParseException("invalid member comment in line " + lineNumber);
						}

						String comment = unescape(parts[1]);

						if (inMethod) {
							visitor.visitMethodComment(comment);
						} else {
							visitor.visitFieldComment(comment);
						}
					} else {
						// TODO: Check read options and warn about unknown keys?
					}
				} else if (indent == 3) {
					// method parameter/variable comment: c <comment>
					if ((inMethodParam || inMethodVar) && section.equals("c")) {
						if (parts.length != 2) {
							throw new ParseException("invalid method var comment in line " + lineNumber);
						}

						String comment = unescape(parts[1]);

						if (inMethodParam) {
							visitor.visitMethodParameterComment(comment);
						} else {
							visitor.visitMethodLocalVariableComment(comment);
						}
					} else {
						// TODO: Check read options and warn about unknown keys?
					}
				} else {
					// TODO: Check read options and warn about unknown keys?
				}
			}

			visitor.visitEnd();
 		}
	}

	private static String[] splitAtTab(String s, int offset, int partCountHint) {
		String[] ret = new String[Math.max(1, partCountHint)];
		int partCount = 0;
		int pos;

		while ((pos = s.indexOf('\t', offset)) >= 0) {
			if (partCount == ret.length) {
				ret = Arrays.copyOf(ret, ret.length * 2);
			}

			ret[partCount++] = s.substring(offset, pos);
			offset = pos + 1;
		}

		if (partCount == ret.length) {
			ret = Arrays.copyOf(ret, ret.length + 1);
		}

		ret[partCount++] = s.substring(offset);

		return partCount == ret.length ? ret : Arrays.copyOf(ret, partCount);
	}

	private static String unescapeOpt(String str, boolean escapedNames) {
		return escapedNames ? unescape(str) : str;
	}

	private static String unescape(String str) {
		int pos = str.indexOf('\\');
		if (pos < 0) return str;

		StringBuilder ret = new StringBuilder(str.length() - 1);
		int start = 0;

		do {
			ret.append(str, start, pos);
			pos++;
			int type;

			if (pos >= str.length()) {
				throw new RuntimeException("incomplete escape sequence at the end");
			} else if ((type = ESCAPED.indexOf(str.charAt(pos))) < 0) {
				throw new RuntimeException("invalid escape character: \\" + str.charAt(pos));
			} else {
				ret.append(TO_ESCAPE.charAt(type));
			}

			start = pos + 1;
		} while ((pos = str.indexOf('\\', start)) >= 0);

		ret.append(str, start, str.length());

		return ret.toString();
	}

	@Override
	public void close() throws Exception {
		this.reader.close();
	}

	private static final class NameGetterImpl implements MappingsVisitor.NameGetter {
		private final int offset;
		private final int namespaceCount;
		private final String[] parts;
		private final boolean escapedStrings;

		NameGetterImpl(int offset, int namespaceCount, String[] parts, boolean escapedStrings) {
			this.offset = offset;
			this.namespaceCount = namespaceCount;
			this.parts = parts;
			this.escapedStrings = escapedStrings;
		}

		@Override
		public String get(int namespaceIndex) {
			if (0 > namespaceIndex) {
				throw new IllegalArgumentException(String.format("Namespace index cannot be negative! Inputted index was %s", namespaceIndex));
			}

			if (namespaceIndex > this.namespaceCount) {
				throw new IndexOutOfBoundsException(String.format("Namespace index out of bounds! Maximum is %s and input was %s", this.namespaceCount - 1, namespaceIndex));
			}

			int index = this.offset + namespaceIndex;

			while (this.parts[index].isEmpty() && index > 0) {
				index--;
			}

			// TODO

			return null;
		}

		@Override
		public String getRaw(int namespaceIndex) {
			// TODO
			return null;
		}

		@Override
		public String[] getRawNames() {
			// TODO
			return new String[0];
		}

		@Override
		public String[] getAllNames() {
			// TODO
			return new String[0];
		}
	}
}
