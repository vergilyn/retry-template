package com.vergilyn.retry.api.common.uniapp;

import com.vergilyn.retry.api.common.ApiResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <a href="https://uniapp.dcloud.net.cn/uniCloud/uni-cloud-push/api.html">uni-app 消息推送</a>
 * <pre>
 *  返回数据结构：
 *  	推送结果
 *      successed_offline: 离线下发(包含厂商通道下发)，
 *      successed_online: 在线下发，
 *      successed_ignore: 最近90天内不活跃设备不下发
 *  {@code
 *		{
 *		  "errCode": 0,
 *		  "errMsg": "",
 *		  "data": {
 *		    "$taskid": {
 *		      "$cid": "$status"
 *		    }
 *		  }
 *		}
 *  }
 *
 *  例如
 *  {@code
 *  {
 *   "data": {
 *     "RASL_0130_5b4ce5ac5c8a4e05bdebf7f82fe916ef": {
 *       "185634c1e0928a460835b973db3544cd": "successed_online",
 *       "185634c1e0928a460835b973db3544cd01": "target user is invalid"
 *     }
 *   },
 *   "errCode": 0,
 *   "errMsg": "success"
 *  }
 *  }
 * </pre>
 *
 * @author dingmaohai
 * @version v1.0
 * @since 2023/01/31 10:39
 */
@Setter
@Getter
@NoArgsConstructor
public class UniAppSendMessageResponse implements ApiResponse {

	private static final long serialVersionUID = 1465186824146616050L;

	public static final String CODE_SUCCESS = "0";

	/**
	 * 推送结果：离线下发(包含厂商通道下发)
	 */
	public static final String CODE_DATA_SUCCESSED_OFFLINE = "successed_offline";

	/**
	 * 在线下发
	 */
	public static final String CODE_DATA_SUCCESSED_ONLINE = "successed_online";

	/**
	 * 最近90天内不活跃设备不下发
	 */
	public static final String CODE_DATA_SUCCESSED_IGNORE = "successed_ignore";

	private String errCode;
	private String errMsg;

	private String data;

	@Override
	public boolean isSuccess() {
		return CODE_SUCCESS.equals(this.errCode);
	}

	@Override
	public String getErrorCode() {
		return errCode;
	}

	@Override
	public String getErrorMessage() {
		return errMsg;
	}
}
