package com.vergilyn.retry.api.common;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 5590685116244306954L;

	private final Integer errCode;

	protected ApiException(Integer errCode, String errMsg) {
		this(errCode, errMsg, null);
	}

	protected ApiException(Integer errCode, String errMsg, Throwable throwable){
		super(errMsg, throwable);

		this.errCode = errCode;
	}

	public Integer getErrCode() {
		return errCode;
	}

}
