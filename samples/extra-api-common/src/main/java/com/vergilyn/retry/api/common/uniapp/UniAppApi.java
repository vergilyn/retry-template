package com.vergilyn.retry.api.common.uniapp;


/**
 * uniapp API
 */
public interface UniAppApi {

	/**
	 * <a href="https://uniapp.dcloud.net.cn/uniCloud/uni-cloud-push/api.html">uni-app 消息推送</a>
	 */
	UniAppSendMessageResponse sendMessage(UniAppSendMessageRequest request, String logPrefix);
}
