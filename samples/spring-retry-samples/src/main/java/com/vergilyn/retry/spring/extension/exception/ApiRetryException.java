package com.vergilyn.retry.spring.extension.exception;

import org.springframework.retry.RetryContext;

public class ApiRetryException extends RuntimeException {

	private final RetryContext retryContext;

	public ApiRetryException(RetryContext retryContext) {
		this.retryContext = retryContext;
	}

	public ApiRetryException(RetryContext retryContext, Throwable cause) {
		super(cause);
		this.retryContext = retryContext;
	}

	public ApiRetryException(RetryContext retryContext, String message) {
		super(message);
		this.retryContext = retryContext;
	}
}
