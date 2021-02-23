package com.vkakarla.springboot.exceptionhandling.junits.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.vkakarla.springboot.exceptionhandling.junits.exception.ErrorCode;
import com.vkakarla.springboot.exceptionhandling.junits.exception.ServiceException;

@Component
public class PersonRequestValidationUtil {
	
	
		public void verifyPersonId(String personId) throws ServiceException{
			
			 if (StringUtils.isEmpty(personId) || personId.equals("undefined") || personId.equals("{personId}")) {
				 throw new ServiceException(ErrorCode.SERVICE_001, new Object[] {personId});
			 }
		 }
		
		public void verifyPersonSSN(Long SSN) throws ServiceException{
			 if ( !(SSN instanceof Long) || SSN==0 || SSN ==null) {
				 throw new ServiceException(ErrorCode.SERVICE_001, new Object[] {SSN});
			 }
		 }
		
	}


