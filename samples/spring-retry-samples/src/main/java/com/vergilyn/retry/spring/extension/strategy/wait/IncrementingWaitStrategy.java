package com.vergilyn.retry.spring.extension.strategy.wait;

import com.google.common.base.Preconditions;
import com.vergilyn.retry.spring.extension.ApiRetryContext;
import com.vergilyn.retry.spring.extension.strategy.WaitStrategy;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class IncrementingWaitStrategy implements WaitStrategy {
	private final long initialSleepTime;
	private final long increment;

	public IncrementingWaitStrategy(long initialSleepTime,
			long increment) {
		Preconditions.checkArgument(initialSleepTime >= 0L, "initialSleepTime must be >= 0 but is %d", initialSleepTime);
		this.initialSleepTime = initialSleepTime;
		this.increment = increment;
	}

	@Override
	public long computeSleepTime(ApiRetryContext apiRetryContext, Integer attemptNumber) {
		long result = initialSleepTime + (increment * (attemptNumber - 1));
		return result >= 0L ? result : 0L;
	}
}
