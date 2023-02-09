package com.vergilyn.examples.retry;

import com.github.rholder.retry.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class RetryerTests {

	/**
	 * 最少 RetryerBuilder 需要的build参数，参考：{@link RetryerBuilder#build()}
	 * <pre>
	 *     {@linkplain AttemptTimeLimiter} -> {@linkplain AttemptTimeLimiters#noTimeLimit()}
	 *     {@linkplain StopStrategy} -> {@linkplain StopStrategies#neverStop()}
	 *     {@linkplain WaitStrategy} -> {@linkplain WaitStrategies#noWait()}
	 *     {@linkplain BlockStrategy} -> {@linkplain BlockStrategies#threadSleepStrategy()}
	 * </pre>
	 */
	@Test
	public void minRetryerBuilder() throws ExecutionException, RetryException {

		Retryer<Long> retryer = RetryerBuilder.<Long>newBuilder().build();

		retryer.call(new SleepyOut(0));
	}

	@Test
	public void allBuilder(){
		Retryer<Integer> retryer = RetryerBuilder.<Integer>newBuilder()
				// 内部是 `Predicates.or(this, Predicate)`，支持同时使用多个`retryIfXxx`
				.retryIfException()
				.retryIfRuntimeException()
				.retryIfException(null)
				.retryIfExceptionOfType(null)
				.retryIfResult(null)

				.withStopStrategy(null)
				.withBlockStrategy(null)
				.withWaitStrategy(null)
				// 重试限制器（每一次执行的时间）
				.withAttemptTimeLimiter(null)
				// 内部是 `list.add()`，可以多次调用
				.withRetryListener(null)
				.build();
	}

	static class SleepyOut implements Callable<Long> {

		final long sleepMs;

		SleepyOut(long sleepMs) {
			this.sleepMs = sleepMs;
		}

		@Override
		public Long call() throws Exception {
			System.out.printf("%s prepare sleep: %s ms \n", LocalDateTime.now(), sleepMs);
			Thread.sleep(sleepMs);
			System.out.printf("%s complete sleep: %s ms \n", LocalDateTime.now(), sleepMs);
			return sleepMs;
		}
	}
}
