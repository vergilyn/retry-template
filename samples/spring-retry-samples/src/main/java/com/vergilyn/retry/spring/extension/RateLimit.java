package com.vergilyn.retry.spring.extension;

import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;

public interface RateLimit {

	boolean tryAcquire(ApiRetryContext apiRetryContext, RetryContext retryContext);

	RetryTemplate getRetryTemplate();
}
