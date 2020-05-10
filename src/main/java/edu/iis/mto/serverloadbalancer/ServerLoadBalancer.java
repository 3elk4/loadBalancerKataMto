package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class ServerLoadBalancer {

	public void balance(Server[] servers, VM[] vms) {
		if(vms.length > 0){
			for(VM vm : vms){
				addToLessLoadedServer(servers, vm);
			}
		}
	}

	private void addToLessLoadedServer(Server[] servers, VM vm) {
		List<Server> capableServers = findCapableServers(servers, vm);
		Server lessLoadedServer = findLessLoadedServer(capableServers);
		if(lessLoadedServer != null) lessLoadedServer.addVM(vm);
	}

	private List<Server> findCapableServers(Server[] servers, VM vm) {
		List<Server> capableServers = new ArrayList<>();
		for (Server server : servers) {
			if(server.canFit(vm)){
				capableServers.add(server);
			}
		}
		return capableServers;
	}

	private Server findLessLoadedServer(List<Server> servers) {
		Server lessLoadedServer = null;
		for (Server server : servers) {
			if(lessLoadedServer == null || server.getCurrentLoadPercentage() < lessLoadedServer.getCurrentLoadPercentage()){
				lessLoadedServer = server;
			}
		}
		return lessLoadedServer;
	}
}
