package edu.iis.mto.serverloadbalancer;

public class ServerBuilder implements Builder<Server> {
	private int capacity;

	public ServerBuilder withCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	@Override
	public Server build() {
		Server server = new Server();
		server.setCapacity(this.capacity);
		return server;
	}
}
