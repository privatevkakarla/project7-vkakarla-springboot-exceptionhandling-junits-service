package com.vkakarla.springboot.exceptionhandling.junits.handler;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.vkakarla.springboot.exceptionhandling.junits.apierror.ErrorMessageGenerator;
import com.vkakarla.springboot.exceptionhandling.junits.apierror.ServiceError;
import com.vkakarla.springboot.exceptionhandling.junits.dto.EntityNotFoundException;
import com.vkakarla.springboot.exceptionhandling.junits.dto.ServiceException;

import lombok.extern.slf4j.Slf4j;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	ServiceResponseHandler responseHandler;
	
	@Autowired
	ErrorMessageGenerator errorMessageGenerator;
	
	

	/* *//**
			 * Handle MissingServletRequestParameterException. Triggered when a 'required'
			 * request parameter is missing.
			 *
			 * @param ex      MissingServletRequestParameterException
			 * @param headers HttpHeaders
			 * @param status  HttpStatus
			 * @param request WebRequest
			 * @return the ApiError object
			 */

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ServiceError serviceError = new ServiceError();
		return responseHandler.buildErrorResponseEntity(serviceError, HttpStatus.BAD_REQUEST);

	}

	/**
	 * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is
	 * invalid as well.
	 *
	 * @param ex      HttpMediaTypeNotSupportedException
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ServiceError serviceError = new ServiceError();
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

		serviceError.setMessage(builder.substring(0, builder.length() - 2));
		return responseHandler.buildErrorResponseEntity(serviceError, HttpStatus.UNSUPPORTED_MEDIA_TYPE);

	}

	/**
	 * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid
	 * validation.
	 *
	 * @param ex      the MethodArgumentNotValidException that is thrown when @Valid
	 *                validation fails
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ServiceError serviceError = new ServiceError();

		serviceError.addValidationErrors(ex.getBindingResult().getFieldErrors());
		serviceError.addValidationError(ex.getBindingResult().getGlobalErrors());
		return responseHandler.buildErrorResponseEntity(serviceError, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles javax.validation.ConstraintViolationException. Thrown when @Validated
	 * fails.
	 *
	 * @param ex the ConstraintViolationException
	 * @return the ApiError object
	 */

	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(javax.validation.ConstraintViolationException ex) {
		ServiceError serviceError = new ServiceError();
		serviceError.setMessage("Validation error");
		serviceError.addValidationErrors(ex.getConstraintViolations());

		return responseHandler.buildErrorResponseEntity(serviceError, HttpStatus.BAD_REQUEST);

	}

	/**
	 * Handles EntityNotFoundException. Created to encapsulate errors with more
	 * detail than javax.persistence.EntityNotFoundException.
	 *
	 * @param ex the EntityNotFoundException
	 * @return the ApiError object
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
		ServiceError serviceError = new ServiceError();
		serviceError.setMessage(ex.getMessage());
		return responseHandler.buildErrorResponseEntity(serviceError, HttpStatus.NOT_FOUND);

	}
	
	

	/**
	 * Handle HttpMessageNotReadableException. Happens when request JSON is
	 * malformed.
	 *
	 * @param ex      HttpMessageNotReadableException
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ServiceError serviceError = new ServiceError();

		serviceError.setMessage("Malformed JSON request");
		return responseHandler.buildErrorResponseEntity(serviceError, HttpStatus.BAD_REQUEST);

	}

	/**
	 * Handle HttpMessageNotWritableException.
	 *
	 * @param ex      HttpMessageNotWritableException
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ServiceError serviceError = new ServiceError();

		serviceError.setMessage("Error writing JSON output");
		return responseHandler.buildErrorResponseEntity(serviceError, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	/**
	 * Handle NoHandlerFoundException.
	 *
	 * @param ex
	 * @param headers
	 * @param status
	 * @param request
	 * @return
	 */

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ServiceError serviceError = new ServiceError();
		serviceError.setMessage(
				String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));

		return responseHandler.buildErrorResponseEntity(serviceError, HttpStatus.BAD_REQUEST);

	}

	/**
	 * Handle javax.persistence.EntityNotFoundException
	 */

	@ExceptionHandler(javax.persistence.EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(javax.persistence.EntityNotFoundException ex) {
		ServiceError serviceError = new ServiceError();
		serviceError.setMessage(ex.getLocalizedMessage());
		return responseHandler.buildErrorResponseEntity(serviceError, HttpStatus.BAD_REQUEST);

	}

	/**
	 * Handle DataIntegrityViolationException, inspects the cause for different DB
	 * causes.
	 *
	 * @param ex the DataIntegrityViolationException
	 * @return the ApiError object
	 */

	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
			WebRequest request) {

		ServiceError serviceError = new ServiceError();

		if (ex.getCause() instanceof ConstraintViolationException) {
			serviceError.setMessage(String.format("Database error", ex.getCause()));
			return responseHandler.buildErrorResponseEntity(serviceError, HttpStatus.CONFLICT);

		}
		return responseHandler.buildErrorResponseEntity(serviceError, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	/**
	 * Handle Exception, handle generic Exception.class
	 *
	 * @param ex the Exception
	 * @return the ApiError object
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {

		ServiceError serviceError = new ServiceError();
		serviceError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));

		return responseHandler.buildErrorResponseEntity(serviceError, HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(ServiceException.class)
	protected ResponseEntity<Object> handleunknownException(ServiceException serviceException) {
		
		
		ServiceError serviceError = new ServiceError();
		serviceException = errorMessageGenerator.generateError(serviceException);
		
		serviceError.setMessage(serviceException.getShortMessage());
		serviceError.setDebugMessage(serviceException.getDetailedMessage());
		
		return responseHandler.buildErrorResponseEntity(serviceError, serviceException.getStatus());

	}

}
