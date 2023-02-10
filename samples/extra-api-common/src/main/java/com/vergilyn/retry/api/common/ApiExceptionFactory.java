package com.vergilyn.retry.api.common;

/**
 * 异常code：<br/>
 * 1. `41xxx` 请求API之前，例如，请求前校验必传参数<br/>
 * 2. `42xxx` 已发送API请求，但请求异常。例如网络原因导致请求失败等<br/>
 * 3. `43xxx` 请求API成功（正确拿到API返回结果）， 例如 请求成功但解析response异常。<br/>
 */
public class ApiExceptionFactory {

	/**
	 * 请求API前，检测必传参数失败。
	 */
	public static final Integer ERROR_CODE_CHECK_FAILURE = 41001;

	/**
	 * 请求API失败。例如网络问题等
	 */
	public static final Integer ERROR_CODE_REQUEST_FAILURE = 42001;


	/**
	 * 请求API成功，但返回异常。
	 *
	 * <p> 例如 umeng返回 `502 Bad Gateway`
	 */
	public static final Integer ERROR_CODE_RESPONSE_FAILURE = 43000;


	/**
	 * 请求API成功，但结果解析异常
	 */
	public static final Integer ERROR_CODE_RESPONSE_PARSE = 43001;

	public static ApiException newCheckException(String message){
		return new ApiException(ERROR_CODE_CHECK_FAILURE, message);
	}

	public static ApiException newRequestException(Throwable t){
		return  new ApiException(ERROR_CODE_REQUEST_FAILURE, t.getMessage(), t);
	}

	public static ApiException newResponseParseException(Throwable t){
		return new ApiException(ERROR_CODE_RESPONSE_PARSE, t.getMessage(), t);
	}

	public static ApiException newResponseException(String message){
		return new ApiException(ERROR_CODE_RESPONSE_FAILURE, message);
	}
}
