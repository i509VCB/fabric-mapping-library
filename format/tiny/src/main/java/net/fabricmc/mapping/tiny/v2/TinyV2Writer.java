package net.fabricmc.mapping.tiny.v2;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.mapping.MappingFormat;
import net.fabricmc.mapping.MappingWriter;
import net.fabricmc.mapping.MappingsVisitor;
import net.fabricmc.mapping.util.Collections2;

final class TinyV2Writer implements MappingWriter {
	private static final List<String> DEFAULT_NAMESPACES = Collections2.pairList(
			MappingFormat.OBFUSCATED_NAMESPACE,
			MappingFormat.DEOBFUSCATED_NAMESPACE
	);

	@Override
	public void write(Writer writer, Consumer<MappingsVisitor> mappings) throws IOException {
		try {
			mappings.accept(new WritingVisitor(writer));
		} catch (SilentIOException e) {
			throw e.loud;
		}
	}

	private static final class WritingVisitor implements MappingsVisitor {
		private final Writer writer;
		private WriteState state = WriteState.HEADER;
		private boolean wroteHeader = false;
		private final List<String> namespaces = new ArrayList<>();

		WritingVisitor(Writer writer) {
			this.writer = writer;
		}

		@Override
		public void visitClass(NameGetter names) {
			if (this.state != WriteState.HEADER || this.state != WriteState.PROPERTIES) {
				throw new IllegalStateException(); // TODO: Message
			}

			// Write the header and properties at first class state
			if (!this.wroteHeader) {

			}

			this.state = WriteState.CLASS;
		}

		@Override
		public void visitMethod(NameGetter names, String descriptor) {
		}

		@Override
		public void visitField(NameGetter names, String descriptor) {
		}

		@Override
		public void visitClassComment(String comment) {
		}

		@Override
		public void visitFieldComment(String comment) {
		}

		@Override
		public void visitMethodComment(String comment) {
			if (this.state != WriteState.METHOD) {
				throw new IllegalStateException(); // TODO: Msg
			}
		}

		@Override
		public void visitMethodParameterComment(String comment) {
			if (this.state != WriteState.METHOD) {
				throw new IllegalStateException(); // TODO: Msg
			}
		}

		@Override
		public void visitNamespaces(List<String> namespaces) {
			if (this.state != WriteState.HEADER) {
				throw new IllegalStateException(); // TODO: Msg
			}

			this.namespaces.addAll(namespaces);
		}

		@Override
		public void visitMethodParameter(NameGetter names, int localVariableIndex) {
			if (this.state != WriteState.METHOD) {
				throw new IllegalStateException(); // TODO: Msg
			}
		}

		@Override
		public void visitMethodLocalVariable(NameGetter names, int localVariableIndex, int localVariableStartOffset, int localVariableTableIndex) {
			if (this.state != WriteState.METHOD) {
				throw new IllegalStateException(); // TODO: Msg
			}
		}

		@Override
		public void visitMethodLocalVariableComment(String comment) {
			if (this.state != WriteState.METHOD) {
				throw new IllegalStateException(); // TODO: Msg
			}
		}

		@Override
		public void visitProperty(String key, @Nullable String value) {
			this.assertBeforeClass();

			// Exit header state
			if (this.state == WriteState.HEADER) {
				if (this.wroteHeader) {
					// Header has been written since visitNamespaces has been called

				}
			}
		}

		@Override
		public void visitEnd() {
		}

		private void assertBeforeClass() {
			if (this.state != WriteState.HEADER || this.state != WriteState.PROPERTIES) {
				throw new IllegalStateException(""); // TODO: Message
			}
		}

		private void write(String value) {
			try {
				this.writer.write(value);
			} catch (IOException e) {
				throw new SilentIOException(e);
			}
		}

		private void tab() {
			try {
				this.writer.write('\t');
			} catch (IOException e) {
				throw new SilentIOException(e);
			}
		}
	}

	enum WriteState {
		HEADER,
		PROPERTIES,
		CLASS,
		METHOD,
		FIELD
	}

	private static final class SilentIOException extends RuntimeException {
		private final IOException loud;

		SilentIOException(IOException loud) {
			this.loud = loud;
		}
	}
}
