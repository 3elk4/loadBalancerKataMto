package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class VMCounterMatcher extends TypeSafeMatcher<Server> {
	private int vmCounter;

	public VMCounterMatcher(int vmCounter) {
		this.vmCounter = vmCounter;
	}

	@Override
	protected boolean matchesSafely(Server server) {
		return server.getVMCounter() == vmCounter;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("Server with VM's count of: ").appendValue(this.vmCounter);
	}

	@Override
	protected void describeMismatchSafely(Server item, Description description) {
		description.appendText("Server with VM's count of: ").appendValue(item.getVMCounter());
	}

	public static VMCounterMatcher hasVmCountOf(int vmCounter) {
		return new VMCounterMatcher(vmCounter);
	}
}
