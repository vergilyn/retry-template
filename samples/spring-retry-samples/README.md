# spring-retry-samples

1. 可以根据`RetryListener#open` 来实现 rate-limit。
切记：需要返回`true`，至少实际调用1次API接口。


## TODO
1. 如果`RetryTemplate`无法使用**单例**。那么更建议通过参数的形式传入`RetryContext`。

2. 为什么要将`RetryContext`设计成 `ThreadLocal`？

3. `RetryContext.parent` 的用途是？

4. `RetryTemplate#execute` 中是否会生成 `RetryContext.parent`？


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

