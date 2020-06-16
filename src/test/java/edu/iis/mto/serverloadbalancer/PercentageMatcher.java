package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class PercentageMatcher extends TypeSafeMatcher<Server> {
	private final double percentage;

	private PercentageMatcher(double percentage) {
		this.percentage = percentage;
	}

	@Override
	protected boolean matchesSafely(Server server) {
		return server.getPercentage() == percentage;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("Load percentage of server: ").appendValue(this.percentage);
	}

	public static PercentageMatcher hasLoadPercentageOf(double percentage) {
		return new PercentageMatcher(percentage);
	}
}
