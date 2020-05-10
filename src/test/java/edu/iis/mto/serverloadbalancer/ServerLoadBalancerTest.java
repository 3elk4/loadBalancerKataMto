package edu.iis.mto.serverloadbalancer;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Test;

public class ServerLoadBalancerTest {
	@Test
	public void itCompiles() {
		assertThat(true, equalTo(true));
	}

	@Test
	public void checkIfServerIsEmptyAndNoVM() {
		Server server = buildServer(0);
		balancing(serverList(server), emptyVMsList());
		assertThat(server, CurrentLoadPercentageMatcher.hasCurrentLoadPercentage(0.0));
	}

	@Test
	public void checkIfServerWithOneSlotCapacityAndOneVMFillsWholeServer() {
		Server server = buildServer(1);
		VM vm = buildVM(1);
		balancing(serverList(server), vmsList(vm));
		assertThat(server, CurrentLoadPercentageMatcher.hasCurrentLoadPercentage(100.0));
		assertThat("Server should contains the one vm.", server.contains(vm));
	}

	@Test
	public void checkIfServerWithOneSlotCapacityAndOneVMFillsServerPartially() {
		Server server = buildServer(10);
		VM vm = buildVM(1);
		balancing(serverList(server), vmsList(vm));
		assertThat(server, CurrentLoadPercentageMatcher.hasCurrentLoadPercentage(10.0));
		assertThat("Server should contains the one vm.", server.contains(vm));
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

	private Server buildServer(int capacity) {
		return Server.builder().withCapacity(capacity).build();
	}

	private VM buildVM(int size) {
		return VM.builder().withSize(size).build();
	}
}
