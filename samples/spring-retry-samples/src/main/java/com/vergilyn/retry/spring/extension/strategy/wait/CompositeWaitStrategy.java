package com.vergilyn.retry.spring.extension.strategy.wait;

import com.google.common.base.Preconditions;
import com.vergilyn.retry.spring.extension.ApiRetryContext;
import com.vergilyn.retry.spring.extension.strategy.WaitStrategy;

import javax.annotation.concurrent.Immutable;
import java.util.List;

@Immutable
public final class CompositeWaitStrategy implements WaitStrategy {
	private final List<WaitStrategy> waitStrategies;

	public CompositeWaitStrategy(List<WaitStrategy> waitStrategies) {
		Preconditions.checkState(!waitStrategies.isEmpty(), "Need at least one wait strategy");
		this.waitStrategies = waitStrategies;
	}

	@Override
	public long computeSleepTime(ApiRetryContext apiRetryContext, Integer attemptNumber) {
		long waitTime = 0L;
		for (WaitStrategy waitStrategy : waitStrategies) {
			waitTime += waitStrategy.computeSleepTime(apiRetryContext, attemptNumber);
		}
		return waitTime;
	}
}