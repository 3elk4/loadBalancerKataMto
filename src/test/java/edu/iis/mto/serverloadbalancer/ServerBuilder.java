package edu.iis.mto.serverloadbalancer;

public class ServerBuilder implements Builder<Server> {
	private int capacity;
	private VM vm;

	public ServerBuilder withCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	@Override
	public Server build() {
		Server server = new Server();
		server.setCapacity(this.capacity);
		if(vm != null) server.addVM(vm);
		return server;
	}

	public ServerBuilder withVmOfSize(int size) {
		VM vm = new VM();
		vm.setSize(size);
		this.vm = vm;
		return this;
	}
}
