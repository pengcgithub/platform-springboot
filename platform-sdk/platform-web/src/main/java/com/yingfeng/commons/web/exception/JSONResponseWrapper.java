package com.yingfeng.commons.web.exception;

import com.alibaba.fastjson.JSON;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * json请求异常<br/>
 *
 * @author pengc
 * @since 2017/09/05
 */
@ControllerAdvice(annotations = RestController.class)
@Order(value = Ordered.LOWEST_PRECEDENCE)
public class JSONResponseWrapper implements ResponseBodyAdvice<Object> {
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		ResultMessage.ResultMessageBuilder builder = ResultMessage.builder().success(true).code("0").message("success");
		if (body instanceof MappingJacksonValue) {
			MappingJacksonValue jacksonValue = (MappingJacksonValue) body;
			jacksonValue.setValue(builder.data(jacksonValue.getValue()).build());
			return jacksonValue;
		}
		else {
			Object data;
			try {
				//try to revert json style
				data = JSON.parse(ObjectUtils.getDisplayString(body));
			}
			catch (Exception e) {
				//if failed use normal
				data = body;
			}
			return JSON.toJSONString(builder.data(data).build());
		}
	}
}
