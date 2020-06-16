package edu.iis.mto.serverloadbalancer;


import static edu.iis.mto.serverloadbalancer.PercentageMatcher.hasLoadPercentageOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class ServerLoadBalancerTest {
	@Test
	public void itCompiles() {
		assertThat(true, equalTo(true));
	}

	@Test
	public void shouldBalanceOneEmptyServerNoVMS() {
		Server server = server().withCapacity(1).build();
		balancing(listOfServers(server), emptyListOfVMs());
		assertThat(server, hasLoadPercentageOf(0.0d));
	}

	@Test
	public void shouldFullBalanceOneEmptyServerOneVMS() {
		Server server = build(server().withCapacity(1));
		VM vm = build(vm().withSize(1));
		balancing(listOfServers(server), listOfVMs(vm));
		assertThat(server, hasLoadPercentageOf(100.0d));
		assertThat("server should contain vm", server.contains(vm));
	}

	private <T> T build(Builder<T> builder) {
		return builder.build();
	}

	private VMBuilder vm() {
		return new VMBuilder();
	}

	private VM[] listOfVMs(VM... vms) {
		return vms;
	}

	private void balancing(Server[] listOfServers, VM[] listOfVMs) {
		new ServerLoadBalancer().balance(listOfServers, listOfVMs);
	}

	private VM[] emptyListOfVMs() {
		return new VM[0];
	}

	private Server[] listOfServers(Server... servers) {
		return servers;
	}

	private ServerBuilder server() {
		return new ServerBuilder();
	}
}
