package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class CurrentLoadPercentageMatcher extends TypeSafeMatcher<Server> {
	private final double expectedLoadPercentage;
	private final double EPSILON = 0.01d;

	public CurrentLoadPercentageMatcher(double expectedLoadPercentage) {
		this.expectedLoadPercentage = expectedLoadPercentage;
	}

	@Override
	protected boolean matchesSafely(Server server) {
		return doublesAreEquals(server.getCurrentLoadPercentage(), expectedLoadPercentage);
	}

	private boolean doublesAreEquals(double d1, double d2) {
		return d1 == d2 || Math.abs(d1 - d2) < EPSILON;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("Server with load percentage of ").appendValue(this.expectedLoadPercentage);
	}

	public static CurrentLoadPercentageMatcher hasCurrentLoadPercentage(double expectedLoadPercentage) {
		return new CurrentLoadPercentageMatcher(expectedLoadPercentage);
	}
}
