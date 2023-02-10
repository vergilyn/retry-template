package com.vergilyn.retry.spring.extension.strategy.wait;

import com.google.common.base.Preconditions;
import com.vergilyn.retry.spring.extension.ApiRetryContext;
import com.vergilyn.retry.spring.extension.strategy.WaitStrategy;

import javax.annotation.concurrent.Immutable;
import java.util.Random;

@Immutable
public final class RandomWaitStrategy implements WaitStrategy {
	private static final Random RANDOM = new Random();
	private final long minimum;
	private final long maximum;

	public RandomWaitStrategy(long minimum, long maximum) {
		Preconditions.checkArgument(minimum >= 0, "minimum must be >= 0 but is %d", minimum);
		Preconditions.checkArgument(maximum > minimum, "maximum must be > minimum but maximum is %d and minimum is", maximum, minimum);

		this.minimum = minimum;
		this.maximum = maximum;
	}

	@Override
	public long computeSleepTime(ApiRetryContext apiRetryContext, Integer attemptNumber) {
		long t = Math.abs(RANDOM.nextLong()) % (maximum - minimum);
		return t + minimum;
	}

}