package edu.iis.mto.serverloadbalancer;

public class ServerLoadBalancerBaseTest {
	protected <T> T build(Builder<T> builder) {
		return builder.build();
	}

	protected ServerBuilder server() {
		return new ServerBuilder();
	}

	protected VMBuilder vm() {
		return new VMBuilder();
	}

	protected VM[] listOfVMs(VM... vms) {
		return vms;
	}

	protected VM[] emptyListOfVMs() {
		return new VM[0];
	}

	protected Server[] listOfServers(Server... servers) {
		return servers;
	}

	protected void balancing(Server[] listOfServers, VM[] listOfVMs) {
		new ServerLoadBalancer().balance(listOfServers, listOfVMs);
	}
}
