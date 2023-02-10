package com.vergilyn.retry.spring.extension.strategy.wait;

import com.google.common.base.Preconditions;
import com.vergilyn.retry.spring.extension.ApiRetryContext;
import com.vergilyn.retry.spring.extension.strategy.WaitStrategy;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class ExponentialWaitStrategy implements WaitStrategy {
	private final long multiplier;
	private final long maximumWait;

	public ExponentialWaitStrategy(long multiplier,
			long maximumWait) {
		Preconditions.checkArgument(multiplier > 0L, "multiplier must be > 0 but is %d", multiplier);
		Preconditions.checkArgument(maximumWait >= 0L, "maximumWait must be >= 0 but is %d", maximumWait);
		Preconditions.checkArgument(multiplier < maximumWait, "multiplier must be < maximumWait but is %d", multiplier);
		this.multiplier = multiplier;
		this.maximumWait = maximumWait;
	}

	@Override
	public long computeSleepTime(ApiRetryContext apiRetryContext, Integer attemptNumber) {
		double exp = Math.pow(2, attemptNumber);
		long result = Math.round(multiplier * exp);
		if (result > maximumWait) {
			result = maximumWait;
		}
		return result >= 0L ? result : 0L;
	}
}