package com.vergilyn.retry.spring.extension.support;

import com.vergilyn.retry.spring.extension.ApiRetryContext;
import org.springframework.retry.RetryContext;

public class QpsRateLimit extends AbstractRateLimit {

	@Override
	protected boolean doTryAcquire(ApiRetryContext apiRetryContext, RetryContext retryContext) {
		return false;
	}
}
