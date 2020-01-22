package com.vkakarla.springboot.exceptionhandling.junits.apierror;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.vkakarla.springboot.exceptionhandling.junits.dto.LowerCaseClassNameResolver;

@JsonInclude(Include.NON_NULL)
@Order(Ordered.HIGHEST_PRECEDENCE)
@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
@Component
public class ServiceError {

	private Object message;
	private String debugMessage;
    private List<ServiceValidationError> validationErrors;
    
    private void addValidationError(ServiceValidationError platformValidationError) {
        if (validationErrors == null) {
        	validationErrors = new ArrayList<>();
        }
        validationErrors.add(platformValidationError);
    }

    private void addValidationError(String object, String field, Object rejectedValue, String message) {
    	addValidationError(new ServiceValidationError(object, field, rejectedValue, message));
    }

    private void addValidationError(String object, String message) {
    	addValidationError(new ServiceValidationError(object, message));
    }

    private void addValidationError(FieldError fieldError) {
        this.addValidationError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }

    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ObjectError objectError) {
        this.addValidationError(
                objectError.getObjectName(),
                objectError.getDefaultMessage());
    }

    public void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    /**
     * Utility method for adding error of ConstraintViolation. Usually when a @Validated validation fails.
     *
     * @param cv the ConstraintViolation
     */
    private void addValidationError(ConstraintViolation<?> cv) {
        this.addValidationError(
                cv.getRootBeanClass().getSimpleName(),
                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
                cv.getInvalidValue(),
                cv.getMessage());
    }

    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }


	public List<ServiceValidationError> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(List<ServiceValidationError> validationErrors) {
		this.validationErrors = validationErrors;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public String getDebugMessage() {
		return debugMessage;
	}

	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}
	
	
}

