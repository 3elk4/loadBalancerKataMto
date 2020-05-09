package edu.iis.mto.serverloadbalancer;

public class Server {
	private double currentLoadPercentage;
	private int capacity;

	private Server(Builder builder){
		this.capacity = builder.capacity;
	}

	public static Builder builder() {
		return new Builder();
	}

	public double getCurrentLoadPercentage() {
		return currentLoadPercentage;
	}

	public static final class Builder {
		private int capacity;

		public Builder withCapacity(int capacity) {
			this.capacity = capacity;
			return this;
		}

		public Server build() {
			return new Server(this);
		}
	}
}
