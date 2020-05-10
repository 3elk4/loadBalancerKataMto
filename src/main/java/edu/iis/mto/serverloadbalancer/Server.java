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
		if(builder.currentLoadPercentage > 0){
			int vmSize = (int)(builder.currentLoadPercentage / MAXIMUM_LOAD * (double) builder.capacity);
			VM vm = VM.builder().withSize(vmSize).build();
			this.addVM(vm);
		}
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
		return VMs.contains(vm);
	}

	public int getCapacity() {
		return capacity;
	}

	public int getVMCounter() {
		return VMs.size();
	}

	public void addVM(VM vm) {
		this.currentLoadPercentage += loadOfVM(vm);
		this.VMs.add(vm);
	}

	private double loadOfVM(VM vm) {
		return vm.getSize() / (double)capacity * MAXIMUM_LOAD;
	}

	public boolean canFit(VM vm) {
		return currentLoadPercentage + loadOfVM(vm) <= MAXIMUM_LOAD;
	}

	public static final class Builder {
		private int capacity;
		private double currentLoadPercentage;

		public Builder withCapacity(int capacity) {
			this.capacity = capacity;
			return this;
		}

		public Builder withCurrentLoadPercentage(double percentageLoad) {
			this.currentLoadPercentage = percentageLoad;
			return this;
		}

		public Server build() {
			return new Server(this);
		}
	}
}
