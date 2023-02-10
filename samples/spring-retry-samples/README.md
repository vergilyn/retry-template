# spring-retry-samples

1. 可以根据`RetryListener#open` 来实现 rate-limit。
切记：需要返回`true`，至少实际调用1次API接口。



## 吐槽
1. `RetryListener`中的方法有杂乱。
```java
public interface RetryListener {

	<T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback);

	<T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable);

	default <T, E extends Throwable> void onSuccess(RetryContext context, RetryCallback<T, E> callback, T result) {
	}

	<T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable);

}
```
比如 rate-limit-RetryListener 只需要实现`#open()`