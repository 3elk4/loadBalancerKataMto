package edu.iis.mto.serverloadbalancer;

public class VM {
	private int size;

	private VM(Builder builder) {
		this.size = builder.size;
	}

	public static Builder builder() { return new Builder(); }

	public int getSize() {
		return size;
	}

	public static final class Builder {
		private int size;

		public Builder withSize(int size) {
			this.size = size;
			return this;
		}

		public VM build() {
			return new VM(this);
		}
	}
}
