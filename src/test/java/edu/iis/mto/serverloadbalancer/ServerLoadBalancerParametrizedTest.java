package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasLoadPercentageOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ServerLoadBalancerParametrizedTest extends ServerLoadBalancerBaseTest{
	@Parameterized.Parameters
	public static Collection<Object[]> balancingValues() {
		return Arrays.asList(new Object[][] { { 1, 1, 100.0 },
											{ 10, 5, 50.0 },
											{ 5, 4, 80.0 },
											{ 10, 3, 30.0 } });
	}

	@Parameterized.Parameter public int capacity;
	@Parameterized.Parameter(1) public int size;
	@Parameterized.Parameter(2) public double percentage;

	@Test
	public void balancingOneServerWithOneSlotCapacity_andOneSlotVm_fillsTheServerWithTheVm() {
		Server theServer = a(server().withCapacity(capacity));
		Vm theVm = a(vm().ofSize(size));
		balance(aListOfServersWith(theServer), aListOfVmsWith(theVm));

		assertThat(theServer, hasLoadPercentageOf(percentage));
		assertThat("the server should contain vm", theServer.contains(theVm));
	}
	
	
}
