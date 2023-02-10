package com.vergilyn.retry.api.common.douyin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.vergilyn.retry.api.common.ApiException;
import com.vergilyn.retry.api.common.ApiExceptionFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="https://developer.open-douyin.com/docs/resource/zh-CN/mini-app/develop/server/subscribe-notification/notify/">
 *     抖音API, 发送订阅消息</a>
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DouyinMiniSubscribeMessageRequest extends DouyinBaseApiRequest<DouyinMiniSubscribeMessageResponse> {

	private static final long serialVersionUID = -5475349798657664750L;

	/**
	 * 【必填】小程序的 id
	 */
	@JSONField(name = "app_id")
	private String appId;

	/**
	 * 【必填】模板的 id
	 */
	@JSONField(name = "tpl_id")
	private String tplId;

	/**
	 * 【必填】接收消息目标用户的 open_id
	 */
	@JSONField(name = "open_id")
	private String openId;

	/**
	 * 【必填】模板内容，格式形如 { "key1": "value1", "key2": "value2" }
	 */
	@JSONField(name = "data")
	private Map<String, String> data = new HashMap<>();

	/**
	 * 【可选】跳转的页面
	 */
	@JSONField(name = "page")
	private String page;

	@Override
	public Class<DouyinMiniSubscribeMessageResponse> responseClass() {
		return DouyinMiniSubscribeMessageResponse.class;
	}

	@Override
	public boolean check() throws ApiException {
		List<String> notnull = Lists.newArrayList();

		addNotNull(notnull, "app_id", () -> StringUtils.isBlank(this.appId));
		addNotNull(notnull, "tpl_id", () -> StringUtils.isBlank(this.tplId));
		addNotNull(notnull, "open_id", () -> StringUtils.isBlank(this.openId));
		addNotNull(notnull, "data", () -> MapUtils.isEmpty(this.data));

		if (!notnull.isEmpty()){
			throw ApiExceptionFactory.newCheckException(String.format("必填参数%s不能为空", ArrayUtils.toString(notnull)));
		}

		return super.check();
	}

	@Override
	public String toPostJsonBody() {
		return JSON.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect);
	}

}



