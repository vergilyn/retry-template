package com.vergilyn.retry.spring;

import org.junit.jupiter.api.Test;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;

/**
 * 根据执行异常判断是否需要重试
 */
public class ExceptionRetryTests {

	final RetryCallback<Integer, Throwable> retryCallback = context -> {
		System.out.println("retryCallback >>> retryCount: " + context.getRetryCount());
		return 1/0;
	};

	/**
	 * 指定异常类型不重试
	 */
	@Test
	public void notRetry() throws Throwable {
		RetryTemplate retryTemplate = RetryTemplate.builder()
				.notRetryOn(ArithmeticException.class)
				.build();

		retryTemplate.execute(retryCallback);
	}

	/**
	 * 指定异常类型不重试
	 */
	@Test
	public void retry() throws Throwable {
		RetryTemplate retryTemplate = RetryTemplate.builder()
				.retryOn(ArithmeticException.class)
				.build();

		retryTemplate.execute(retryCallback);
	}
}
