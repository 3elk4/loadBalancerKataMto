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

	private void balancing(Server[] servers, VM[] VMs) {
		new ServerLoadBalancer().balance(servers, VMs);
	}

	private VM[] emptyVMsList() {
		return new VM[0];
	}

	private Server[] serverList(Server ...servers) {
		return servers;
	}

	private Server buildServer(int capacity) {
		return Server.builder().withCapacity(0).build();
	}
}
