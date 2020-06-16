package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;

public class Server {
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
		this.vms.add(vm);
	}

	public boolean contains(VM vm) {
		return this.vms.contains(vm);
	}
}
