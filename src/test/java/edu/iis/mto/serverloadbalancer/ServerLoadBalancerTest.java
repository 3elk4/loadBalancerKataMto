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
	public void shouldFullBalanceOneEmptyServerOneVM() {
		Server server = build(server().withCapacity(1));
		VM vm = build(vm().withSize(1));
		balancing(listOfServers(server), listOfVMs(vm));
		assertThat(server, hasLoadPercentageOf(100.0d));
		assertThat("server should contain vm", server.contains(vm));
	}

	@Test
	public void shouldPartlyBalanceOneEmptyServerOneVM() {
		Server server = build(server().withCapacity(10));
		VM vm = build(vm().withSize(5));
		balancing(listOfServers(server), listOfVMs(vm));
		assertThat(server, hasLoadPercentageOf(50.0d));
		assertThat("server should contain vm", server.contains(vm));
	}

	@Test
	public void shouldPartlyBalanceOneEmptyServerTwoVMs() {
		Server server = build(server().withCapacity(10));
		VM vm1 = build(vm().withSize(5));
		VM vm2 = build(vm().withSize(2));
		balancing(listOfServers(server), listOfVMs(vm1, vm2));
		assertThat(server, hasLoadPercentageOf(70.0d));
		assertThat("server should contain vm", server.contains(vm1));
		assertThat("server should contain vm", server.contains(vm2));
	}

	@Test
	public void shouldBalanceToLessLoadedServerTwoServersOneVm() {
		Server server1 = build(server().withCapacity(10).withVmOfSize(1));
		Server server2 = build(server().withCapacity(10).withVmOfSize(5));
		VM vm = build(vm().withSize(5));
		balancing(listOfServers(server1, server2), listOfVMs(vm));
		assertThat(server1, hasLoadPercentageOf(60.0d));
		assertThat(server2, hasLoadPercentageOf(50.0d));
		assertThat("server should contain vm", server1.contains(vm));
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
