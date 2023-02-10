package com.vergilyn.retry.api.common.douyin;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <a href="https://developer.open-douyin.com/docs/resource/zh-CN/mini-app/develop/server/share/url-link-generate/#_%E5%93%8D%E5%BA%94%E5%8F%82%E6%95%B0">
 *     生成能够直接跳转到端内小程序的 url link。</a>
 *
 */
@Setter
@Getter
@NoArgsConstructor
public class DouyinUrlSchemaResponse extends DouyinBaseApiResponse{
	private static final long serialVersionUID = 1155986982209250093L;

	@JSONField(name = "url_link")
	private String urlLink;
}
