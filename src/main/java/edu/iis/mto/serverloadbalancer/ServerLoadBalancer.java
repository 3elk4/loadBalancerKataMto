package edu.iis.mto.serverloadbalancer;

public class ServerLoadBalancer {

	public void balance(Server[] servers, VM[] vms) {
		if(vms.length > 0){
			for(VM vm : vms){
				addToLessLoadedServer(servers, vm);
			}
		}
	}

	private void addToLessLoadedServer(Server[] servers, VM vm) {
		Server lessLoadedServer = findLessLoadedServer(servers);
		lessLoadedServer.addVM(vm);
	}

	private Server findLessLoadedServer(Server[] servers) {
		Server lessLoadedServer = null;
		for (Server server : servers) {
			if(lessLoadedServer == null || server.getCurrentLoadPercentage() < lessLoadedServer.getCurrentLoadPercentage()){
				lessLoadedServer = server;
			}
		}
		return lessLoadedServer;
	}
}
