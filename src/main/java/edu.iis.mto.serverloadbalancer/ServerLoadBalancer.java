package edu.iis.mto.serverloadbalancer;

public class ServerLoadBalancer {
	public void balance(Server[] listOfServers, VM[] listOfVMs) {
		for (VM vm : listOfVMs) {
			listOfServers[0].addVM(vm);
		}
	}
}
