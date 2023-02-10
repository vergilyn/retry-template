package com.vergilyn.retry.spring.extension.listener;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.listener.RetryListenerSupport;

/**
 * 线程不安全！
 */
public class LastResultRetryListener extends RetryListenerSupport {
	private Object lastResult;

	@Override
	public <T, E extends Throwable> void onSuccess(RetryContext context, RetryCallback<T, E> callback, T result) {
		this.lastResult = result;
	}
}
