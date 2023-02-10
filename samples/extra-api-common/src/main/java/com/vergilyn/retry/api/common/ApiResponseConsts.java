package com.vergilyn.retry.api.common;

public class ApiResponseConsts {
	public static final String RESP_ID_UNKNOWN = "unknown";

	public static final Integer RESP_CODE_SUCCESS = 200;
	public static final Integer RESP_CODE_FAILURE = 100;

	/**
	 * mcs-dubbo 构建消息错误。（未真实请求过API）
	 */
	public static final Integer RESP_CODE_CHECK_FAIL = 400100;

	public static final String RESP_MSG_SUCCESS = "发送成功";
	public static final String RESP_MSG_FAILURE = "发送失败";
}
