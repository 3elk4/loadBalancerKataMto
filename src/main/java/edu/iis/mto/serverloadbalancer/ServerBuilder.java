package edu.iis.mto.serverloadbalancer;

import org.junit.internal.runners.statements.FailOnTimeout;

public class ServerBuilder implements Builder<Server> {
	private int capacity;
	private double percentage;

	public static ServerBuilder builder() {
		return new ServerBuilder();
	}

	public ServerBuilder withCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public ServerBuilder withLoadOf(double percentage) {
		this.percentage = percentage;
		return this;
	}

	public Server build() {
		Server server = new Server(this.capacity);
		if(this.percentage > 0) addInitialLoad(server);
		return server;
	}

	private void addInitialLoad(Server server) {
		int vmSize = (int)(this.percentage / Server.FULL_SIZE * this.capacity);
		server.addVm(VMBuilder.builder().withSize(vmSize).build());
	}
}
