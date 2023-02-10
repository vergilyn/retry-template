package com.vergilyn.retry.api.common.douyin;

import com.vergilyn.retry.api.common.ApiException;

/**
 * 抖音小程序
 *
 */
public interface DouyinMiniProgramApi {


	/**
	 * <a href="https://opendocs.alipay.com/mini/02cth2">小程序发送模板消息</a>
	 *
	 * @return
	 */
	DouyinMiniSubscribeMessageResponse sendSubscribeMsg(DouyinMiniSubscribeMessageRequest request, String logPrefix)
			throws ApiException;

	/**
	 * <a href="https://developer.open-douyin.com/docs/resource/zh-CN/mini-app/develop/server/share/url-link-generate/">
	 *     抖音，生成能够直接跳转到端内小程序的 url link</a>
	 */
	DouyinUrlSchemaResponse generateUrlSchema(DouyinBaseApiRequest<DouyinUrlSchemaResponse> request) throws ApiException;
}
