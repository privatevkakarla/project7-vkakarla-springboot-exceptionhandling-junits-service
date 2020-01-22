package com.vkakarla.springboot.exceptionhandling.junits.service;

import com.vkakarla.springboot.exceptionhandling.junits.dto.ServiceException;
import com.vkakarla.springboot.exceptionhandling.junits.entities.Person;

public interface IPersonService {

	
	public Person createPerson(Person person)throws ServiceException;
	
	public Person getPersonByPersonId(String personId) throws ServiceException;
	
	public Person getPersonBySSN(long ssn);
	
}
