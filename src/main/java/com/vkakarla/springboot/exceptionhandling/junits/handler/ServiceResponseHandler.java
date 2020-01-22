package com.vkakarla.springboot.exceptionhandling.junits.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.vkakarla.springboot.exceptionhandling.junits.apierror.ServiceError;
import com.vkakarla.springboot.exceptionhandling.junits.dto.ServiceResponse;

@Component
public class ServiceResponseHandler {

	public ResponseEntity<Object> buildErrorResponseEntity(ServiceError serviceError, HttpStatus status) {

		ServiceResponse ServiceResponse = new ServiceResponse();
		ServiceResponse.setStatus(status);
		ServiceResponse.setServiceError(serviceError);
		return new ResponseEntity<>(ServiceResponse, status);
	}

	public ResponseEntity<ServiceResponse> buildResponseEntity(Object data, HttpStatus status) {
		ServiceResponse ServiceResponse = new ServiceResponse();
		ServiceResponse.setStatus(status);
		ServiceResponse.setData(data);
		return new ResponseEntity<>(ServiceResponse, status);
	}

}
