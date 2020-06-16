package edu.iis.mto.serverloadbalancer;

public class VMBuilder implements Builder<VM> {

	private int size;

	public VMBuilder withSize(int size) {
		this.size = size;
		return this;
	}

	@Override
	public VM build() {
		VM vm = new VM();
		vm.setSize(this.size);
		return vm;
	}
}
