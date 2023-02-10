package com.vergilyn.retry.spring.extension.support;

import com.vergilyn.retry.spring.extension.ApiRetryContext;
import com.vergilyn.retry.spring.extension.RateLimit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.listener.RetryListenerSupport;
import org.springframework.retry.support.RetryTemplate;

@Slf4j
public abstract class AbstractRateLimit implements RateLimit {

	protected abstract boolean doTryAcquire(ApiRetryContext apiRetryContext, RetryContext retryContext);

	@Override
	public boolean tryAcquire(ApiRetryContext apiRetryContext, RetryContext retryContext) {
		log.info("\t [vergilyn] >>>> {}#doTryAcquire(), retryCount: {}",
		                  this.getClass().getSimpleName(),
		                  retryContext.getRetryCount());

		return doTryAcquire(apiRetryContext, retryContext);
	}

	@Override
	public RetryTemplate getRetryTemplate() {
		return RetryTemplate.builder()
				.maxAttempts(5)
				.uniformRandomBackoff(1000, 3000)
				.notRetryOn(Throwable.class)
				.withListener(new RetryListenerSupport() {

					@Override
					public <T, E extends Throwable> void onSuccess(RetryContext context, RetryCallback<T, E> callback, T result) {
						log.info("\t [vergilyn] >>>> {}#onSuccess(), retryCount: {}, result: {} ",
						                  "AbstractRateLimit",
						                  context.getRetryCount(),
						                  result);

						// throw new ApiRetryException(context, "retry-by-onSuccess");
					}
				})
				.build();
	}
}
