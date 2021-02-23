package com.vkakarla.springboot.exceptionhandling.junits.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.vkakarla.springboot.exceptionhandling.junits.apierror.ErrorMessageGenerator;
import com.vkakarla.springboot.exceptionhandling.junits.apierror.ServiceError;
import com.vkakarla.springboot.exceptionhandling.junits.dto.ServiceException;

import lombok.extern.slf4j.Slf4j;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

	
	
	@Autowired
	ErrorMessageGenerator errorMessageGenerator;
	
	@ExceptionHandler(ServiceException.class)
	protected ResponseEntity<Object> handleunknownException(ServiceException serviceException) {
		
		ServiceError serviceError = new ServiceError();
		serviceException = errorMessageGenerator.generateError(serviceException);
		
		serviceError.setMessage(serviceException.getShortMessage());
		serviceError.setDebugMessage(serviceException.getDetailedMessage());
		
		return new ResponseEntity<Object>(serviceError, serviceException.getStatus());

	}

}
