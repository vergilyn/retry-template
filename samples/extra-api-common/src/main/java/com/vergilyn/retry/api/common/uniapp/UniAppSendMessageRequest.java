package com.vergilyn.retry.api.common.uniapp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vergilyn.retry.api.common.ApiException;
import com.vergilyn.retry.api.common.ApiExceptionFactory;
import com.vergilyn.retry.api.common.ApiRequest;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * <a href="https://uniapp.dcloud.net.cn/uniCloud/uni-cloud-push/api.html">uni-app 消息推送</a>
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UniAppSendMessageRequest implements ApiRequest<UniAppSendMessageResponse> {

	private static final long serialVersionUID = 1351678033618073051L;

	public static final String KEY_PARAM_appId = "appId";
	public static final String KEY_PARAM_push_clientid = "push_clientid";
	public static final String KEY_PARAM_title = "title";
	public static final String KEY_PARAM_content = "content";
	public static final String KEY_PARAM_requestId = "request_id";
	public static final String KEY_PARAM_payload = "payload";

	/**
	 * 【必填】 并不是uniapp的AppId（例如 `fYCmavnMIP9dkj2GdRXvq8`），而是指`__UNI__E421DF2`
	 */
	private String appId;

	/**
	 * 【可选】 透传数据
	 */
	private Map<String, String> payload;

	/**
	 * 【必填】基于uni.getPushClientId获取的客户端推送标识，指定接收消息的设备。
	 * 支持多个以数组的形式指定多个设备，如["cid-1","cid-2"]，数组长度不大于1000
	 */
	private List<String> pushClientids;

	/**
	 * 【必填】通知栏标题，长度小于20
	 */
	private String title;

	/**
	 * 【必填】通知栏内容，长度小于50
	 */
	private String content;

	/**
	 * 【必填】请求唯一标识号，10-32位之间；如果request_id重复，会导致消息丢失
	 */
	private String requestId;

	/**
	 * 【必填】特殊，uniapp2.0 云函数URL化的"推送消息"地址。
	 */
	private String apiCloudUrl;

	@Override
	public Class<UniAppSendMessageResponse> responseClass() {
		return UniAppSendMessageResponse.class;
	}

	@Override
	public boolean check() throws ApiException {
		List<String> notnull = Lists.newArrayList();

		addNotNull(notnull, KEY_PARAM_appId, () -> StringUtils.isBlank(this.appId));
		addNotNull(notnull, KEY_PARAM_push_clientid, () -> CollectionUtils.isEmpty(this.pushClientids));
		addNotNull(notnull, KEY_PARAM_title, () -> StringUtils.isBlank(this.title));
		addNotNull(notnull, KEY_PARAM_content, () -> StringUtils.isBlank(this.content));
		addNotNull(notnull, KEY_PARAM_requestId, () -> StringUtils.isBlank(this.requestId));
		addNotNull(notnull, "api-cloud-url", () -> StringUtils.isBlank(this.apiCloudUrl));

		if (!notnull.isEmpty()){
			throw ApiExceptionFactory.newCheckException(String.format("必填参数%s不能为空", ArrayUtils.toString(notnull)));
		}

		return true;
	}

	@Override
	public String toPostJsonBody() {
		Map<String, Object> jsonBody = Maps.newHashMap();
		jsonBody.put(KEY_PARAM_appId, this.appId);
		jsonBody.put(KEY_PARAM_push_clientid, this.pushClientids);
		jsonBody.put(KEY_PARAM_title, this.title);
		jsonBody.put(KEY_PARAM_content, this.content);
		jsonBody.put(KEY_PARAM_requestId, this.requestId);
		jsonBody.put(KEY_PARAM_payload, this.payload);

		return JSON.toJSONString(jsonBody, SerializerFeature.DisableCircularReferenceDetect);
	}
}
