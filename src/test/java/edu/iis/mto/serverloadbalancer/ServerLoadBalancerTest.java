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
