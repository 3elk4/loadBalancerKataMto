package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class ServerLoadBalancer {
	public void balance(Server[] servers, VM[] vms) {
		for (VM vm : vms) {
			List<Server> enoughCapacityServers = findServersWithEnoughCapacity(servers, vm);
			addToCapableLessLoadedServer(enoughCapacityServers, vm);
		}
	}

	private void addToCapableLessLoadedServer(List<Server> enoughCapacityServers, VM vm) {
		Server lessLoadedServer = extractLessLoadedServer(enoughCapacityServers);
		if(lessLoadedServer != null)
			lessLoadedServer.addVm(vm);
	}

	private List<Server> findServersWithEnoughCapacity(Server[] servers, VM vm) {
		List<Server> enoughServers = new ArrayList<>();
		for (Server server : servers) {
			if(server.canFit(vm)){
				enoughServers.add(server);
			}
		}
		return enoughServers;
	}

	private Server extractLessLoadedServer(List<Server> servers) {
		Server lessLoadedServer = null;
		for (Server server : servers) {
			if(lessLoadedServer == null || server.getLoadPercentage() < lessLoadedServer.getLoadPercentage())
				lessLoadedServer = server;
		}
		return lessLoadedServer;
	}
}
