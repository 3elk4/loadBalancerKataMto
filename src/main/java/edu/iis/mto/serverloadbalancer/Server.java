package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {
	public static final double FULL_SIZE = 100.0;
	public List<VM> vms = new ArrayList<>();
	private int capacity = 0;
	private double loadPercentage;

	public int getCapacity() {
		return capacity;
	}

	public double getLoadPercentage() {
		return loadPercentage;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setLoadPercentage(double loadPercentage) {
		this.loadPercentage = loadPercentage;
	}

	public Server(int capacity) {
		this.capacity = capacity;
	}

	public void addVm(VM vm) {
		loadPercentage += vmLoad(vm);
		vms.add(vm);
	}

	private double vmLoad(VM vm) {
		return (double)vm.getSize() / (double)capacity * FULL_SIZE;
	}

	public boolean contains(VM vm) {
		return vms.contains(vm);
	}

	public boolean canFit(VM vm) {
		return loadPercentage + vmLoad(vm) <= FULL_SIZE;
	}
}
