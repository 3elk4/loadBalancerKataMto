package edu.iis.mto.serverloadbalancer;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ServerLoadBalancerTest {
	@Parameterized.Parameters
	public static Collection<Object[]> serverAndVmValues(){
		return Arrays.asList(new Object[][]{{10, 10, 50.0, 45.0, 2, 4, 2},
				{5, 20, 40.0, 20.0, 6, 3, 2 }});
	}

	@Parameterized.Parameter public int capacity1;
	@Parameterized.Parameter(1) public int capacity2;
	@Parameterized.Parameter(2) public double load1;
	@Parameterized.Parameter(3) public double load2;
	@Parameterized.Parameter(4) public int size1;
	@Parameterized.Parameter(5) public int size2;
	@Parameterized.Parameter(6) public int size3;

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
		Server firstServer = build(server().withCapacity(capacity1).withLoadOf(load1));
		Server secondServer = build(server().withCapacity(capacity2).withLoadOf(load2));
		VM vm1 = build(vm().withSize(size1));
		VM vm2 = build(vm().withSize(size2));
		VM vm3 = build(vm().withSize(size3));
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
