package edu.iis.mto.serverloadbalancer;

public class ServerLoadBalancer {
	public void balance(Server[] listOfServers, VM[] listOfVMs) {
		for (VM vm : listOfVMs) {
			Server lessLoaded = chooseLessLoadedServer(listOfServers);
			lessLoaded.addVM(vm);
		}
	}

	private Server chooseLessLoadedServer(Server[] listOfServers) {
		Server lessLoaded = null;
		for (Server server : listOfServers) {
			if(lessLoaded == null || server.getPercentage() < lessLoaded.getPercentage()){
				lessLoaded = server;
			}
		}
		return lessLoaded;
	}
}
