package edu.iis.mto.serverloadbalancer;

public class ServerLoadBalancer {

	public void balance(Server[] servers, VM[] vms) {
		if(vms.length > 0){
			servers[0].setCurrentLoadPercentage(100.0d);
		}
	}
}
