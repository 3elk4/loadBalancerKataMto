package edu.iis.mto.serverloadbalancer;

public class ServerLoadBalancer {
	public void balance(Server[] listOfServers, VM[] listOfVMs) {
		if(listOfVMs.length == 0) listOfServers[0].setPercentage(0.0d);
		else {
			listOfServers[0].setPercentage(100.0d);
			listOfServers[0].addVM(listOfVMs[0]);
		}
	}
}
