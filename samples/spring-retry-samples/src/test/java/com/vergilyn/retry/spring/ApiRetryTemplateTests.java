package com.vergilyn.retry.spring;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.vergilyn.retry.api.common.ApiResponse;
import com.vergilyn.retry.api.common.douyin.DouyinMiniSubscribeMessageResponse;
import com.vergilyn.retry.api.common.uniapp.UniAppSendMessageResponse;
import com.vergilyn.retry.spring.extension.ApiRetryContext;
import com.vergilyn.retry.spring.extension.RateLimit;
import com.vergilyn.retry.spring.extension.RateLimitRetryListener;
import com.vergilyn.retry.spring.extension.exception.ResultRetryException;
import com.vergilyn.retry.spring.extension.listener.LastResultRetryListener;
import com.vergilyn.retry.spring.extension.support.AbstractRateLimit;
import com.vergilyn.retry.spring.extension.support.QpsRateLimit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.listener.RetryListenerSupport;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.retry.support.RetryTemplate;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

@Slf4j
public class ApiRetryTemplateTests {
	private static final String DOUYIN_CODE_SUCCESS = DouyinMiniSubscribeMessageResponse.CODE_SUCCESS;
	private static final String DOUYIN_CODE_SUCCESS_MSG = "douyin.成功";
	private static final String DOUYIN_CODE_FAILURE = "douyin.100";
	private static final String DOUYIN_CODE_FAILURE_MSG = "douyin.内部错误";

	private static final String UNIAPP_CODE_SUCCESS = UniAppSendMessageResponse.CODE_SUCCESS;
	private static final String UNIAPP_CODE_SUCCESS_MSG = "uniapp.成功";
	private static final String UNIAPP_CODE_FAILURE = "uniapp.100";
	private static final String UNIAPP_CODE_FAILURE_MSG = "uniapp.内部错误";

	final AtomicInteger _indexDouyin = new AtomicInteger(0);
	final AtomicInteger _indexUniapp = new AtomicInteger(0);

	/**
	 * 可以共用。
	 */
	final RetryListener _rateLimitRetryListener = buildRateLimitListener();

	/**
	 * 抖音，连续执行到`resp % 4 == 0`次时，执行成功。
	 */
	final RetryCallback<DouyinMiniSubscribeMessageResponse, Exception> _douyin = new RetryCallback<DouyinMiniSubscribeMessageResponse, Exception>() {
		@Override
		public DouyinMiniSubscribeMessageResponse doWithRetry(RetryContext context) throws Exception {
			int resp = _indexDouyin.incrementAndGet();
			boolean isSuccess = resp % 4 == 0;
			String errorCode = isSuccess ? DOUYIN_CODE_SUCCESS : DOUYIN_CODE_FAILURE;
			String errorMsg = isSuccess ? DOUYIN_CODE_SUCCESS_MSG : DOUYIN_CODE_FAILURE_MSG;

			DouyinMiniSubscribeMessageResponse response = new DouyinMiniSubscribeMessageResponse();
			response.setErrNo(errorCode);
			response.setErrTips(errorMsg);

			return response;
		}
	};

	/**
	 * uniapp。连续执行到`resp % 3 == 0`次时，执行成功。
	 */
	final RetryCallback<UniAppSendMessageResponse, Exception> _uniapp = new RetryCallback<UniAppSendMessageResponse, Exception>() {
		@Override
		public UniAppSendMessageResponse doWithRetry(RetryContext context) throws Exception {
			int resp = _indexUniapp.incrementAndGet();
			boolean isSuccess = resp % 3 == 0;
			String errorCode = isSuccess ? UNIAPP_CODE_SUCCESS : UNIAPP_CODE_FAILURE;
			String errorMsg = isSuccess ? UNIAPP_CODE_SUCCESS_MSG : UNIAPP_CODE_FAILURE_MSG;

			UniAppSendMessageResponse response = new UniAppSendMessageResponse();
			response.setErrCode(errorCode);
			response.setErrMsg(errorMsg);
			response.setData(null);

			return response;
		}
	};

	/**
	 * <p> 1. 通过 {@linkplain RetryListener#open(RetryContext, RetryCallback)} 增强 retryContext，保存后续需要的 api-request-params。
	 * <pre>
	 *     用途：从 api-request-param 获取 流控标识，比如微信公众号AppId等。
	 *     1）同一个请求存在多种流控规则，即存在多个 流控标识。
	 *
	 *     注意：请求参数可能是`send(param1)` 或者 `send(param1, param2...)`
	 * </pre>
	 *
	 * <p><h2>重要</h2>
	 *
	 * <p> 1. 在对外API接口请求时，RetryTemplate 不太能使用 单例。
	 *   <br/> 目前(2023-02-10)想法，针对每个API接口可以使用相同的 RetryTemplate.instance。
	 */
	@ParameterizedTest
	@ValueSource(ints = {3, 5})
	public void douyin(Integer maxAttempts){
		LastResultRetryListener lastResultRetryListener = new LastResultRetryListener();

		RetryTemplate template = RetryTemplate.builder()
				// callback 的重试次数
				.maxAttempts(maxAttempts)
				// callback 的重试间隔
				.fixedBackoff(1000)
				.retryOn(ResultRetryException.class)
				// `Listener`是倒序执行！！！
				.withListener(buildRetryByResult(apiResponse -> {
					String[] retryCodes = { DOUYIN_CODE_FAILURE };
					return ArrayUtils.contains(retryCodes, apiResponse.getErrorCode());
				}))
				// 实现 流控规则
				.withListener(_rateLimitRetryListener)
				// （暂时2023-02-10推荐方式）用于获取根据执行结果重试时，最后1次的执行结果。线程不安全，需要最先执行此 RetryListener！
				.withListener(lastResultRetryListener)
				.build();

		// `RetryTemplate#execute(...)` 前初始化自定义的 RetryContext
		//  根据API接口指定 RateLimit's
		ApiRetryContext apiRetryContext = buildApiRetryContext(Lists.newArrayList(
				// 1.1 添加QPS限流
				new QpsRateLimit(),

				// 1.2 添加自定义限流
				new AbstractRateLimit() {
					@Override
					protected boolean doTryAcquire(ApiRetryContext apiRetryContext, RetryContext retryContext) {
						return false;
					}
				}
		));
		// （根据javadoc描述，不推荐这么调用`register`方法。
		//      并且其目的只是注册 RateLimit's，完全可以直接通过`RateLimitRetryListener#open()`实现，
		//      不需要将 RateLimit's 提前注册到 RetryContext 中）
		// 通过 RetrySynchronizationManager#register 提前注册自定义的 RetryContext。
		//  1) 添加 RateLimitListener
		RetrySynchronizationManager.register(apiRetryContext);

		try {
			ApiResponse resp = template.execute(_douyin);
			log.warn("[vergilyn] SUCCESS last-result: {}", JSON.toJSONString(resp, true));

		} catch (Exception e) {
			if (e instanceof ResultRetryException){
				ResultRetryException re = (ResultRetryException) e;
				// 这种方式 并不推荐，原因：
				//   当存在多个 RetryListener's 时，需要执行到相应的 XxRetryListener 才会抛出此异常。
				//   此时需要严格 RetryListener's 的执行顺序。
				// 所以，更推荐用 LastResultRetryListener 来达到此目的

				log.warn("[vergilyn] FAILURE last-result-by-ResultRetryException: {}", JSON.toJSONString(re.getResult(), true));
			}

			// 推荐方式：
			log.warn("[vergilyn] FAILURE last-result-by-LastResultRetryListener: {}", JSON.toJSONString(lastResultRetryListener.getLastResult(), true));

			e.printStackTrace();
		}
	}

	private ApiRetryContext buildApiRetryContext(Collection<RateLimit> rateLimits){
		ApiRetryContext apiRetryContext = new ApiRetryContext(null);
		apiRetryContext.addRateLimit(rateLimits);
		return apiRetryContext;
	}

	private RetryListener buildRateLimitListener(){
		return new RateLimitRetryListener();
	}

	private RetryListener buildRetryByResult(Function<ApiResponse, Boolean> retrySupplier){
		return new RetryListenerSupport(){
			@Override
			public <T, E extends Throwable> void onSuccess(RetryContext context, RetryCallback<T, E> callback, T result) {
				if (result == null){
					throw new ResultRetryException(result, "执行结果为空，进行重试");
				}

				// `null instanceof ApiResponse` 始终返回 false
				if (!(result instanceof ApiResponse)){
					return;
				}

				// 如果是最后一次重试，则返回结果（而不是重试异常）
				// 2023-02-10 因为存在多种 retry-policy，所以无法这么判断！
				// if (context.getRetryCount() == maxAttempts){
				// 	return;
				// }

				// 根据执行结果判断是否需要重试。如果需要重试，则抛出一个允许重试的异常。
				ApiResponse resp = (ApiResponse) result;

				if (!resp.isSuccess() && retrySupplier.apply(resp)){
					throw new ResultRetryException(result, "执行结果不满足预期，进行重试");
				}
			}
		};
	}

}
