package com.vergilyn.retry.api.common.email;

import com.vergilyn.retry.api.common.ApiResponse;
import com.vergilyn.retry.api.common.ApiResponseConsts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Setter
@Getter
@NoArgsConstructor
public class EmailApiResponse implements ApiResponse {

	private static final long serialVersionUID = -4646578079836518485L;

	private Boolean result;

	private String message;

	@Override
	public boolean isSuccess() {
		return this.result != null && this.result;
	}

	public static EmailApiResponse success(){
		EmailApiResponse response = new EmailApiResponse();
		response.setResult(true);
		response.setMessage(ApiResponseConsts.RESP_MSG_SUCCESS);

		return response;
	}

	public static EmailApiResponse failure(String errMsg){
		EmailApiResponse response = new EmailApiResponse();
		response.setResult(false);
		response.setMessage(StringUtils.isNotBlank(errMsg) ? errMsg : ApiResponseConsts.RESP_MSG_FAILURE);

		return response;
	}
}
