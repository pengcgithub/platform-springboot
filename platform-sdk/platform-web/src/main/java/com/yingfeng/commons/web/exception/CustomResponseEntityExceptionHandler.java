package com.yingfeng.commons.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.List;
import static com.yingfeng.commons.web.exception.ApiException.DEFAULT_ERROR_CODE;

/**
 * customize the JSON document to return for a particular controller and/or exception type.
 *
 * @author pengc on 2016/10/18.
 */
@ControllerAdvice(annotations = RestController.class)
@Slf4j
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Autowired(required = false)
	private List<ExceptionErrorCodeProvider> errorCodeProviders;

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String message = null, errorCode = DEFAULT_ERROR_CODE;
		Object[] args = null;
		boolean handled = false;
		if (ex instanceof ApiException) {
			ApiException ae = (ApiException) ex;
			errorCode = ae.getErrorCode();
			args = ae.getArgs();
			if (ae instanceof SimpleException) {
				message = ExceptionUtils.getMessage(ex);
				handled = true;
			}
		}
		//for bean validation
		else if (ex instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException mae = (MethodArgumentNotValidException) ex;
			BindingResult bindingResult = mae.getBindingResult();
			if (bindingResult != null) {
				List<FieldError> fieldErrors = bindingResult.getFieldErrors();
				StringBuilder sb = new StringBuilder();
				for (FieldError fieldError : fieldErrors) {
					String field = fieldError.getField();
					sb.append(field).append(fieldError.getDefaultMessage()).append(";");
				}
				message = sb.toString();
				errorCode = "argumentEx";
				handled = true;
			}
		}
		else {
			boolean needLog = true;
			//customize other exceptions
			if (!CollectionUtils.isEmpty(errorCodeProviders)) {
				for (ExceptionErrorCodeProvider codeProvider : errorCodeProviders) {
					if (codeProvider.support(ex)) {
						errorCode = codeProvider.getErrorCode();
						needLog = false;
						break;
					}
				}
			}
			if (needLog) {
				//log unexpected exception
				log.error("Unexpected exception occurred", ex);
			}
		}

		if (!handled) {
			//find message from message source
			String messageCode = toMessageCode(errorCode);
			try {
				message = messageSource.getMessage(messageCode, args, request.getLocale());
			}
			catch (NoSuchMessageException e) {
				log.warn("Message not found for message code : {} ", messageCode);
				//root cause for mention
				message = ExceptionUtils.getRootCauseMessage(ex);
			}
		}

		//server error
		return super.handleExceptionInternal(ex,
				new ResultMessage(false, errorCode, message, null), headers, HttpStatus.OK,
				request);

	}

	private String toMessageCode(String errorCode) {
		return "error." + errorCode;
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
		return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
}
