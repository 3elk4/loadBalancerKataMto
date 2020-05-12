package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class PercentageLoadMatcher extends TypeSafeMatcher<Server> {
	private static final double EPSILON = 0.01d;
	private double percentage = 0.0d;

	public PercentageLoadMatcher(double percentage) {
		this.percentage = percentage;
	}

	@Override
	protected boolean matchesSafely(Server server) {
		return loadsAreEquals(server.getLoadPercentage(), percentage);
	}

	private  boolean loadsAreEquals(double d1, double d2){
		return d1 == d2 || Math.abs(d1 - d2) < EPSILON;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("Server with load percentage of: ").appendValue(this.percentage);
	}

	public static PercentageLoadMatcher hasLoadPercentageOf(double percentage) {
		return new PercentageLoadMatcher(percentage);
	}
}
