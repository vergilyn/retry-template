package com.vergilyn.retry.spring.extension;

import com.google.common.collect.Lists;
import org.springframework.retry.RetryContext;
import org.springframework.retry.context.RetryContextSupport;

import java.util.Collection;
import java.util.List;

public class ApiRetryContext extends RetryContextSupport {

	private final List<RateLimit> rateLimits = Lists.newArrayList();

	private boolean isThrowRateLimitException = false;

	public ApiRetryContext(RetryContext parent) {
		super(parent);
	}

	public List<RateLimit> getRateLimits() {
		return rateLimits;
	}

	public List<RateLimit> addRateLimit(RateLimit rateLimit){
		this.rateLimits.add(rateLimit);

		return this.rateLimits;
	}

	public List<RateLimit> addRateLimit(Collection<RateLimit> rateLimits){
		if (rateLimits != null){
			this.rateLimits.addAll(rateLimits);
		}

		return this.rateLimits;
	}

	public ApiRetryContext isThrowRateLimitException(boolean isIgnoreRateLimitException) {
		 this.isThrowRateLimitException = isIgnoreRateLimitException;
		 return this;
	}

	public boolean isThrowRateLimitException() {
		return isThrowRateLimitException;
	}
}
