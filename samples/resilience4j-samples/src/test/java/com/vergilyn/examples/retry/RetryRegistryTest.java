package com.vergilyn.examples.retry;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.Getter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;

class RetryRegistryTest {

    @SneakyThrows
    @Test
    void test_rateLimiter() {

        RateLimiterConfig config = RateLimiterConfig.custom()
                                                    // 限流器每隔limitRefreshPeriod刷新一次，将允许处理的最大请求数量重置为limitForPeriod。
                                                    .limitRefreshPeriod(Duration.ofSeconds(1))
                                                    // 在一次刷新周期内，允许执行的最大请求数
                                                    .limitForPeriod(5)
                                                    // 线程等待权限的默认等待时间
                                                    .timeoutDuration(Duration.ofMillis(25))
                                                    .build();

        RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(config);

        RateLimiter api = rateLimiterRegistry.rateLimiter("api");

        for (int i = 0; i < 10; i++){
            api.executeCallable(() -> {
                System.out.println("executeCallable");

                return null;
            });
        }


    }

    @Test
    void test_retry() throws Exception {
        // RetryRegistry retryRegistry = RetryRegistry.ofDefaults();
        RetryConfig config = RetryConfig.<ApiResponse>custom()
                                        .maxAttempts(2)
                                        .waitDuration(Duration.ofMillis(1000))
                                        .retryOnResult(response -> response.getStatus() == 500)
                                        .retryOnException(e -> e instanceof ApiException)
                                        .retryExceptions(IOException.class, ApiTimeoutException.class)
                                        .ignoreExceptions(ApiIgnoredException.class)
                                        .build();

        RetryRegistry retryRegistry = RetryRegistry.of(config);

        Retry retry = retryRegistry.retry("api");

        retry.executeCallable(() -> {
            System.out.println("executeCallable");

            return new ApiResponse(200);
        });
    }

    @Getter
    private static class ApiResponse {

        private final Integer status;

        public ApiResponse(Integer status) {
            this.status = status;
        }
    }

    private static class ApiException extends RuntimeException {

    }

    private static class ApiTimeoutException extends RuntimeException {

    }

    private static class ApiIgnoredException extends RuntimeException {

    }
}
