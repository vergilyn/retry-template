package com.vergilyn.retry.spring.extension.exception;

import org.springframework.retry.RetryException;

public class ResultRetryException extends RetryException {

	// Generic class may not extend 'java.lang.Throwable'
	private final Object result;

	public ResultRetryException(Object result, String msg) {
		super(msg);
		this.result = result;
	}

	public Object getResult() {
		return result;
	}
}