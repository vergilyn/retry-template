package com.vergilyn.retry.api.common.douyin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import com.vergilyn.retry.api.common.ApiException;
import com.vergilyn.retry.api.common.ApiExceptionFactory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * <a href="https://developer.open-douyin.com/docs/resource/zh-CN/mini-app/develop/server/share/url-link-generate/">
 *     抖音，生成能够直接跳转到端内小程序的 url link</a>
 *
 */
@Setter
@Getter
@NoArgsConstructor
public class DouyinUrlSchemaRequest extends DouyinBaseApiRequest<DouyinUrlSchemaResponse>{

	private static final long serialVersionUID = -3618366759766864746L;

	public static final String APP_NAME_douyin = "douyin";
	public static final String APP_NAME_douyinlite = "douyinlite";

	/**
	 * 【必填】小程序ID
	 */
	@JSONField(name = "ma_app_id")
	private String maAppId;

	/**
	 * 【必填】宿主名称。可选 "douyin"、"douyinlite"
	 */
	@JSONField(name = "app_name")
	private String appName;

	/**
	 * 【可选】通过URL Link进入的小程序页面路径，必须是已经发布的小程序存在的页面，不可携带 query。
	 * <b>path 为空时会跳转小程序主页。</b>
	 *
	 * <p> 例如：`pages/index`
	 */
	@JSONField(name = "path")
	private String path;

	/**
	 * 【可选】通过URL Link进入小程序时的 query（json形式），若无请填{}。
	 * 最大1024个字符，只支持数字，大小写英文以及部分特殊字符：`{}!#$&'()*+,/:;=?@-._~%``。
	 *
	 * <p> 例如：`{"id":"123***789"}`
	 */
	@JSONField(name = "query")
	private String query;

	/**
	 * 【必填】到期失效的URL Link的失效时间。为 Unix 时间戳，实际失效时间为距离当前时间小时数，向上取整。最长间隔天数为180天。
	 *
	 * <p> 例如 `1665158399`
	 */
	@JSONField(name = "expire_time")
	private Long expireTime;

	@Override
	public Class<DouyinUrlSchemaResponse> responseClass() {
		return DouyinUrlSchemaResponse.class;
	}

	@Override
	public boolean check() throws ApiException {
		List<String> notnull = Lists.newArrayList();

		addNotNull(notnull, "ma_app_id", () -> StringUtils.isBlank(this.maAppId));
		addNotNull(notnull, "app_name", () -> StringUtils.isBlank(this.appName));
		addNotNull(notnull, "expire_time", () -> this.expireTime == null);

		if (!notnull.isEmpty()){
			throw ApiExceptionFactory.newCheckException(String.format("必填参数%s不能为空", ArrayUtils.toString(notnull)));
		}

		return super.check();
	}

	@Override
	public String toPostJsonBody() {
		return JSON.toJSONString(this);
	}
}
