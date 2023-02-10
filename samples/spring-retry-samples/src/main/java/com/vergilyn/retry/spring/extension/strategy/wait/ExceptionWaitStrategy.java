package com.vergilyn.retry.spring.extension.strategy.wait;

import com.google.common.base.Function;
import com.vergilyn.retry.spring.extension.ApiRetryContext;
import com.vergilyn.retry.spring.extension.strategy.WaitStrategy;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

@Immutable
public final class ExceptionWaitStrategy<T extends Throwable> implements WaitStrategy {
	private final Class<T> exceptionClass;
	private final Function<T, Long> function;

	public ExceptionWaitStrategy(@Nonnull Class<T> exceptionClass, @Nonnull Function<T, Long> function) {
		this.exceptionClass = exceptionClass;
		this.function = function;
	}

	@SuppressWarnings({"ThrowableResultOfMethodCallIgnored", "ConstantConditions", "unchecked"})
	@Override
	public long computeSleepTime(ApiRetryContext apiRetryContext, Integer attemptNumber) {
		// TODO 2023-02-09
		return 0L;
	}
}