# retry-template

## 背景
主要是`流控`和`重试`。

流控：
1) 可能有多个流控规则，且流控规则会被复用。
2) 不同流控规则的 sleep-ms 不一样。

重试：
- 请求异常
- 请求成功，根据返回结果Code判断是否允许重试。（并且也**可能**存在 sleep-ms）

Q1. 流控和重试都会触发等待，区分 rate-times 和 retry-times？
感觉只有 retry-times 比较有意义，记录请求API接口的次数，后续用于分析API接口的稳定性。

## See Also
- spring-retry: <https://github.com/spring-projects/spring-retry>
- guava-retrying（不再维护）: <https://github.com/rholder/guava-retrying>
