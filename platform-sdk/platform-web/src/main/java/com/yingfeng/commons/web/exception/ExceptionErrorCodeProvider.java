package com.yingfeng.commons.web.exception;

import org.springframework.core.ErrorCoded;


public interface ExceptionErrorCodeProvider extends ErrorCoded {
	boolean support(Exception e);
}
