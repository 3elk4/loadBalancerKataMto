package edu.iis.mto.serverloadbalancer;

public class ServerLoadBalancer {

	public void balance(Server[] servers, VM[] vms) {
		if(vms.length > 0){
			for(VM vm : vms){
				servers[0].addVM(vm);
			}
		}
	}
}
