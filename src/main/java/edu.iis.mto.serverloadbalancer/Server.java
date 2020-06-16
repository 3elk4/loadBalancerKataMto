package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;

public class Server {
	private static final double HUNDRET = 100.0;
	private int capacity;
	private double percentage;
	private ArrayList<VM> vms = new ArrayList<>();

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public double getPercentage() {
		return this.percentage;
	}

	public void addVM(VM vm) {
		this.percentage += ((double)vm.getSize() / this.getCapacity()) * HUNDRET;
		this.vms.add(vm);
	}

	public boolean contains(VM vm) {
		return this.vms.contains(vm);
	}

	public double getCapacity() {
		return this.capacity;
	}

	public boolean canAddVm(VM vm) {
		double tempPercentage = this.percentage + ((double)vm.getSize() / this.getCapacity()) * HUNDRET;
		return tempPercentage <= HUNDRET;
	}
}
