package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class VMCounterMatcher extends TypeSafeMatcher<Server> {
	private final int counter;

	public VMCounterMatcher(int counter) {
		this.counter = counter;
	}

	@Override
	protected boolean matchesSafely(Server server) {
		return server.vms.size() == counter;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("Server with vms number of: ").appendValue(this.counter);
	}

	@Override
	protected void describeMismatchSafely(Server item, Description description) {
		description.appendText("Server with vms number of: ").appendValue(item.vms.size());
	}

	public static VMCounterMatcher hasVMCountOf(int counter) {
		return new VMCounterMatcher(counter);
	}
}
