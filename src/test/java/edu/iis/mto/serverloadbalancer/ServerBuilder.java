package edu.iis.mto.serverloadbalancer;

public class ServerBuilder {
	private int capacity;

	public ServerBuilder withCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public Server build() {
		Server server = new Server();
		server.setCapacity(this.capacity);
		return server;
	}
}
