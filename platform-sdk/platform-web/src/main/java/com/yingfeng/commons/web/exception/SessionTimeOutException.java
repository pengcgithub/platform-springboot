package com.yingfeng.commons.web.exception;

/**
 * 业务模块自定义异常
 * @author pengc
 */
public class SessionTimeOutException extends ApiException{

	@Override
	public String getErrorCode() {
		return "20004";
	}

}
