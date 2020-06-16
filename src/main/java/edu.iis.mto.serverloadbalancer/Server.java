package edu.iis.mto.serverloadbalancer;

public class Server {
	private int capacity;
	private double percentage;

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public double getPercentage() {
		return this.percentage;
	}
}
