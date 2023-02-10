package com.vergilyn.retry.spring;

import org.junit.jupiter.api.Test;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.BinaryExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.CompositeRetryPolicy;
import org.springframework.retry.policy.MaxAttemptsRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.retry.support.RetryTemplateBuilder;

public class RetryTemplateTests {

	/**
	 * 根据 {@linkplain RetryTemplateBuilder#build()} 可知：
	 *
	 * <p> 1. {@linkplain RetryPolicy} 固定是：{@linkplain CompositeRetryPolicy}，其中包含<b>2个有序执行</b>的实现：
	 * <pre>
	 *     1) baseRetryPolicy，通过 {@linkplain RetryTemplateBuilder#maxAttempts(int)} 或 {@linkplain RetryTemplateBuilder#customPolicy(RetryPolicy)}
	 *       指定。
	 *
	 *     2) {@linkplain BinaryExceptionClassifierRetryPolicy}
	 * </pre>
	 */
	@Test
	public void test(){
		RetryTemplate template = RetryTemplate.builder()
				// .maxAttempts(3)
				.customPolicy(new MaxAttemptsRetryPolicy(3))
				.fixedBackoff(1000)
				.retryOn(Exception.class)
				// .withListeners()
				.build();

		Integer result = template.execute(retryContext -> {
			// ... do something
			return 1;
		});
	}


}
