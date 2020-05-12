package edu.iis.mto.serverloadbalancer;

public class VMBuilder implements Builder<VM>{
	private int size = 0;

	public static VMBuilder builder() {
		return new VMBuilder();
	}

	public VMBuilder withSize(int size) {
		this.size = size;
		return this;
	}

	public VM build() {
		return new VM(size);
	}
}
