package net.fabricmc.mapping.reader;

public interface BasicReadOptions extends ReadOptions {
	static BasicReadOptions.Builder builder() {
		return new Builder();
	}

	class Builder {
		protected Builder() {
		}

		public BasicReadOptions build() {
			return new BasicReadOptionsImpl();
		}

		protected static class BasicReadOptionsImpl implements BasicReadOptions {
		}
	}
}
