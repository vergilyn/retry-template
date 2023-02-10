package com.vergilyn.retry.api.common.douyin;

import com.alibaba.fastjson.annotation.JSONField;
import com.vergilyn.retry.api.common.ApiResponse;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Setter
@Getter
public abstract class DouyinBaseApiResponse implements ApiResponse {
	private static final long serialVersionUID = 3124841327406557664L;

	public static final String CODE_SUCCESS = "0";

	/**
	 * 错误码。当请求失败时，会返回非 0 的 err_no，错误信息会携带在 err_tips 中，错误码含义如下：
	 */
	@JSONField(name = "err_no")
	private String errNo;

	/**
	 * 错误信息
	 */
	@JSONField(name = "err_tips")
	private String errTips;

	@Override
	public boolean isSuccess() {
		return StringUtils.equalsIgnoreCase(this.errNo, CODE_SUCCESS);
	}

	@Override
	public String getErrorCode() {
		return errNo;
	}

	@Override
	public String getErrorMessage() {
		return errTips;
	}
}
