package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {
	private static final double MAXIMUM_LOAD = 100.0d;
	private double currentLoadPercentage;
	private int capacity;
	private List<VM> VMs = new ArrayList<>();

	private Server(Builder builder){
		this.capacity = builder.capacity;
	}

	public static Builder builder() {
		return new Builder();
	}

	public double getCurrentLoadPercentage() {
		return currentLoadPercentage;
	}
	public void setCurrentLoadPercentage(double currentLoadPercentage) {
		this.currentLoadPercentage = currentLoadPercentage;
	}

	public boolean contains(VM vm) {
		return true;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getVMCounter() {
		return VMs.size();
	}

	public void addVM(VM vm) {
		this.currentLoadPercentage += vm.getSize() / (double)capacity * MAXIMUM_LOAD;
		this.VMs.add(vm);
	}

	public static final class Builder {
		private int capacity;

		public Builder withCapacity(int capacity) {
			this.capacity = capacity;
			return this;
		}

		public Server build() {
			return new Server(this);
		}
	}
}
