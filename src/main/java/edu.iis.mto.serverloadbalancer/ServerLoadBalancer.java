package edu.iis.mto.serverloadbalancer;

public class ServerLoadBalancer {
	public void balance(Server[] listOfServers, VM[] listOfVMs) {
		if(listOfVMs.length == 0) listOfServers[0].setPercentage(0.0d);
		else {
			double percentage = ((double)listOfVMs[0].getSize() / listOfServers[0].getCapacity()) * 100.0;
			listOfServers[0].setPercentage(percentage);
			listOfServers[0].addVM(listOfVMs[0]);
		}
	}
}
