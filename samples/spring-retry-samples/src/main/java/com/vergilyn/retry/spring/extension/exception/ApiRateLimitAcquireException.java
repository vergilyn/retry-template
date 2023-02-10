package com.vergilyn.retry.spring.extension.exception;

import com.vergilyn.retry.spring.extension.ApiRetryContext;

public class ApiRateLimitAcquireException extends ApiRetryException {
	/**
	 * 流控令牌获取次数，从1开始。
	 */
	private final Integer attemptNumber;

	public ApiRateLimitAcquireException(ApiRetryContext apiRetryContext, Integer attemptNumber) {
		super(apiRetryContext);
		this.attemptNumber = attemptNumber;
	}

	public ApiRateLimitAcquireException(ApiRetryContext apiRetryContext, Throwable cause, Integer attemptNumber) {
		super(apiRetryContext, cause);
		this.attemptNumber = attemptNumber;
	}
}
