package com.vergilyn.retry.spring.extension;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.retry.*;
import org.springframework.retry.support.RetryTemplate;

/**
 * 根据 {@linkplain RetryTemplate#doExecute(RetryCallback, RecoveryCallback, RetryState)}
 * 执行顺序是：
 * <ol>
 *     <li>{@linkplain #open(RetryContext, RetryCallback)}</li>
 *     <li>{@linkplain #close(RetryContext, RetryCallback, Throwable)}</li>
 *     <li>{@linkplain #onError(RetryContext, RetryCallback, Throwable)}</li>
 * </ol>
 *
 */
@SuppressWarnings("JavadocReference")
public class RateLimitRetryListener implements RetryListener {
	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * {@linkplain #open(RetryContext, RetryCallback)} 始终返回 `true`，保证强制执行后续流程。
	 */
	private static final boolean OPEN_RESULT = true;


	@Override
	public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
		RetryContext parentContext = context.getParent();

		if (!(parentContext instanceof ApiRetryContext)){
			return OPEN_RESULT;
		}

		ApiRetryContext apiRetryContext = (ApiRetryContext) parentContext;

		// XXX 2023-02-09，以下流控规则是`AND`关系执行，是否需要支持`OR`？
		// 实际业务情况下，多个流控规则需要同时通过后才调用API接口。（并且，流控规则有一定的顺序）
		boolean isAcquired = false;
		for (RateLimit rateLimit : apiRetryContext.getRateLimits()) {

			try {
				isAcquired = rateLimit.getRetryTemplate().execute(ctx -> {
					return rateLimit.tryAcquire(apiRetryContext, ctx);
				});

			}catch (Exception ex){
				// TODO 2023-02-10
			}
		}

		return OPEN_RESULT;
	}

	@Override
	public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback,
			Throwable throwable) {
		// do nothing
	}

	@Override
	public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback,
			Throwable throwable) {
		// do nothing
	}

}
