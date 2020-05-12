package edu.iis.mto.serverloadbalancer;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Assert;
import org.junit.Test;

public class ServerLoadBalancerTest {
	@Test
	public void itCompiles() {
		assertThat(true, equalTo(true));
	}

	@Test
	public void balancingServer_NoVm_ServerStaysEmpty() {
		Server server = build(server().withCapacity(0));
		balancing(listOfServers(server), emptyListOfVMs());
		Assert.assertThat(server, PercentageLoadMatcher.hasLoadPercentageOf(0.0d));
	}

	@Test
	public void balancingOneServerWithOneSlotCapacity_andOneSlotVm_fillsTheServerWithTheVm() {
		Server server = build(server().withCapacity(1));
		VM vm = build(vm().withSize(1));
		balancing(listOfServers(server), listOfVms(vm));
		Assert.assertThat(server, PercentageLoadMatcher.hasLoadPercentageOf(100.0d));
	}

	@Test
	public void balancingOneServerWithTenSlotsCapacity_andOneSlotVm_fillTheServerWithTnPercent() {
		Server server = build(server().withCapacity(10));
		VM vm = build(vm().withSize(1));
		balancing(listOfServers(server), listOfVms(vm));
		Assert.assertThat(server, PercentageLoadMatcher.hasLoadPercentageOf(10.0d));
	}

	@Test
	public void balancingAServerWithEnoughRoom_getsFilledWithAllVms() {
		Server server = build(server().withCapacity(100));
		VM vm1 = build(vm().withSize(2));
		VM vm2 = build(vm().withSize(3));
		balancing(listOfServers(server), listOfVms(vm1, vm2));
		Assert.assertThat(server, VMCounterMatcher.hasVMCountOf(2));
	}

	@Test
	public void aVm_shouldBeBAlanced_onLessLoadedServerFirst() {
		Server firstServer = build(server().withCapacity(10).withLoadOf(50.0));
		Server secondServer = build(server().withCapacity(10).withLoadOf(45.0));
		VM vm = build(vm().withSize(2));
		balancing(listOfServers(firstServer, secondServer), listOfVms(vm));
		Assert.assertTrue(secondServer.contains(vm));
	}

	@Test
	public void balanceAServerWithNotEnoughRoom_shouldNotBeFilledWithAVm() {
		Server server = build(server().withCapacity(10).withLoadOf(90.0));
		VM vm = build(vm().withSize(2));
		balancing(listOfServers(server), listOfVms(vm));
		Assert.assertFalse(server.contains(vm));
	}

	@Test
	public void balance_serversAndVms() {
		Server firstServer = build(server().withCapacity(10).withLoadOf(50.0));
		Server secondServer = build(server().withCapacity(10).withLoadOf(45.0));
		VM vm1 = build(vm().withSize(2));
		VM vm2 = build(vm().withSize(4));
		VM vm3 = build(vm().withSize(2));
		balancing(listOfServers(firstServer, secondServer), listOfVms(vm1, vm2, vm3));
		Assert.assertTrue(secondServer.contains(vm1));
		Assert.assertTrue(firstServer.contains(vm2));
		Assert.assertTrue(secondServer.contains(vm3));
	}

	private <T> T build(Builder<T> builder) {
		return builder.build();
	}

	private VMBuilder vm() {
		return VMBuilder.builder();
	}

	private ServerBuilder server() {
		return ServerBuilder.builder();
	}

	private void balancing(Server[] listOfServers, VM[] emptyListOfVMs) {
		new ServerLoadBalancer().balance(listOfServers, emptyListOfVMs);
	}

	private VM[] emptyListOfVMs() {
		return new VM[0];
	}

	private Server[] listOfServers(Server ...servers) {
		return servers;
	}

	private VM[] listOfVms(VM ...vms) {
		return vms;
	}
}
