package com.vergilyn.retry.spring;

import com.vergilyn.retry.spring.extension.exception.ResultRetryException;
import org.junit.jupiter.api.Test;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.listener.RetryListenerSupport;
import org.springframework.retry.support.RetryTemplate;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 根据执行结果判断是否需要重试
 */
public class ResultRetryTests {

	static AtomicInteger _index = new AtomicInteger(0);

	final RetryCallback<Integer, Throwable> retryCallback = context -> {
		System.out.println("retryCallback >>> retryCount: " + context.getRetryCount());
		return _index.incrementAndGet();
	};

	/**
	 * 根据`result`判断是否需要重试：
	 * <pre>
	 *     通过 {@linkplain RetryListener#onSuccess(RetryContext, RetryCallback, Object)} 判断，
	 *     如果需要重试，则抛出异常。
	 * </pre>
	 *
	 * <p> Q. 如何获取最后1次执行时的`result`？
	 * <pre> 2种方式：
	 *     1. 例如以下代码。`AtomicReference<Integer> lastResultReference`
	 *     2. 抛出的异常中保存 last-result。
	 * </pre>
	 */
	@Test
	public void retry() {
		final String keyLastResult = "LAST_RESULT";
		AtomicReference<Integer> lastResultReference = new AtomicReference<>();

		RetryTemplate retryTemplate = RetryTemplate.builder()
				.maxAttempts(3)
				.withListener(new RetryListenerSupport() {
					@Override
					public <T, E extends Throwable> void onSuccess(RetryContext context, RetryCallback<T, E> callback,
							T result) {

						System.out.printf("RetryListenerSupport#onSuccess >>> retryCount: %s, result: %s \n",
						                  context.getRetryCount(),
						                  result);


						Integer i = (Integer) result;

						context.setAttribute(keyLastResult, result);
						lastResultReference.set(i);

						// 如果根据`result`需要进行重试，则需要抛出异常。
						if (i < 100){
							throw new ResultRetryException(result, "需要重试...");
						}
					}
				})
				.build();

		try {
			retryTemplate.execute(retryCallback);

		} catch (Throwable e) {
			// 方式1：获取最后一次执行结果
			System.out.println("1. lastResult: " + lastResultReference.get());

			// 方式2：获取最后一次执行结果
			if (e instanceof ResultRetryException){
				ResultRetryException resultRetryException = (ResultRetryException) e;
				System.out.println("2. lastResult: " + resultRetryException.getResult());
			}

			// `retryTemplate#execute(...)`执行完成后会调用 `RetrySynchronizationManager.clear()`, 所以无法再获取到 context。
			// System.out.println("context.lastResult: " + RetrySynchronizationManager.getContext().getAttribute(keyLastResult));

			e.printStackTrace();
		}
	}

}
