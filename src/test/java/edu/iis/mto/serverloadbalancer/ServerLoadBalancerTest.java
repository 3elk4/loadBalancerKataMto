package edu.iis.mto.serverloadbalancer;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;
import org.junit.Test;

public class ServerLoadBalancerTest {
	@Test
	public void itCompiles() {
		assertThat(true, equalTo(true));
	}

	@Test
	public void checkIfServerIsEmptyAndNoVM() {
		Server server = server().withCapacity(0).build();
		balancing(serverList(server), emptyVMsList());
		assertThat(server, CurrentLoadPercentageMatcher.hasCurrentLoadPercentage(0.0));
	}

	@Test
	public void checkIfServerWithOneSlotCapacityAndOneVMFillsWholeServer() {
		Server server = server().withCapacity(1).build();
		VM vm = buildVM(1);
		balancing(serverList(server), vmsList(vm));
		assertThat(server, CurrentLoadPercentageMatcher.hasCurrentLoadPercentage(100.0));
		assertThat("Server should contains the one vm.", server.contains(vm));
	}

	@Test
	public void checkIfServerWithOneSlotCapacityAndOneVMFillsServerPartially() {
		Server server = server().withCapacity(10).build();
		VM vm = buildVM(1);
		balancing(serverList(server), vmsList(vm));
		assertThat(server, CurrentLoadPercentageMatcher.hasCurrentLoadPercentage(10.0));
		assertThat("Server should contains the one vm.", server.contains(vm));
	}

	@Test
	public void checkIfServerWithEnoughCapacityAndTwoVMFillsWholeServer() {
		Server server = server().withCapacity(2).build();
		VM vm1 = buildVM(1);
		VM vm2 = buildVM(1);
		balancing(serverList(server), vmsList(vm1, vm2));
		assertThat(server, CurrentLoadPercentageMatcher.hasCurrentLoadPercentage(100.0));
		assertThat(server, VMCounterMatcher.hasVmCountOf(2));
	}

	@Test
	public void checkIfLessLoadedServerIsChosenForVM() {
		Server moreLoadedServer = server().withCapacity(100).withCurrentLoadPercentage(50.0d).build();
		Server lessLoadedServer = server().withCapacity(100).withCurrentLoadPercentage(20.0d).build();
		VM vm = buildVM(10);

		balancing(serverList(moreLoadedServer, lessLoadedServer), vmsList(vm));
		assertThat("Less loaded server should contains the vm.", lessLoadedServer.contains(vm));
		assertThat("More loaded server should not contains the vm.", !moreLoadedServer.contains(vm));
	}

	@Test
	public void checkIfFullServerCanAddNewVMMachine() {
		Server server = server().withCapacity(10).withCurrentLoadPercentage(90.0d).build();
		VM vm = buildVM(2);
		balancing(serverList(server), vmsList(vm));
		assertThat(server, CurrentLoadPercentageMatcher.hasCurrentLoadPercentage(90.0));
		assertThat("Server should not contains the vm.", !server.contains(vm));
	}

	@Test
	public void checkIfVMsDistributedEvenlyToServers() {
		Server firstServer = server().withCapacity(4).build();
		Server secondServer = server().withCapacity(6).build();
		VM vm1 = buildVM(2);
		VM vm2 = buildVM(4);
		VM vm3 = buildVM(2);
		balancing(serverList(firstServer, secondServer), vmsList(vm1, vm2, vm3));
		assertThat("Server should contains the vm1.", firstServer.contains(vm1));
		assertThat("Server should contains the vm3.", firstServer.contains(vm3));
		assertThat("Server should contains the vm2.", secondServer.contains(vm2));
		assertThat(firstServer, VMCounterMatcher.hasVmCountOf(2));
		assertThat(secondServer, VMCounterMatcher.hasVmCountOf(1));
	}

	private VM[] vmsList(VM ...vms) {
		return vms;
	}

	private VM[] emptyVMsList(){
		return new VM[0];
	}

	private void balancing(Server[] servers, VM[] VMs) {
		new ServerLoadBalancer().balance(servers, VMs);
	}

	private Server[] serverList(Server ...servers) {
		return servers;
	}

	private Server.Builder server() {
		return Server.builder();
	}

	private VM buildVM(int size) {
		return VM.builder().withSize(size).build();
	}
}
