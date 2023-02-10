package com.vergilyn.retry.api.common.douyin;

import com.alibaba.fastjson.annotation.JSONField;
import com.vergilyn.retry.api.common.ApiException;
import com.vergilyn.retry.api.common.ApiExceptionFactory;
import com.vergilyn.retry.api.common.ApiRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public abstract class DouyinBaseApiRequest<T extends DouyinBaseApiResponse> implements ApiRequest<T> {

	private static final long serialVersionUID = 3194912839405970302L;

	/**
	 * 【必填】小程序 access_toke
	 */
	@JSONField(name = "access_token")
	protected String accessToken;

	@Override
	public boolean check() throws ApiException {
		if (StringUtils.isBlank(this.accessToken)){
			throw ApiExceptionFactory.newCheckException("`accessToken`不能为空.");
		}

		return true;
	}
}
