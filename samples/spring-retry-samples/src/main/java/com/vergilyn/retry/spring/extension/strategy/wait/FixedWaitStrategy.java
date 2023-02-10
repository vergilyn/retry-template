package com.vergilyn.retry.spring.extension.strategy.wait;

import com.google.common.base.Preconditions;
import com.vergilyn.retry.spring.extension.ApiRetryContext;
import com.vergilyn.retry.spring.extension.strategy.WaitStrategy;

public class FixedWaitStrategy implements WaitStrategy {
	private final long sleepTime;

	public FixedWaitStrategy(long sleepTime) {
		Preconditions.checkArgument(sleepTime >= 0L, "sleepTime must be >= 0 but is %d", sleepTime);
		this.sleepTime = sleepTime;
	}

	@Override
	public long computeSleepTime(ApiRetryContext apiRetryContext, Integer attemptNumber) {
		return sleepTime;
	}
}
