package com.vkakarla.springboot.exceptionhandling.junits.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vkakarla.springboot.exceptionhandling.junits.apierror.ErrorCode;
import com.vkakarla.springboot.exceptionhandling.junits.dto.ServiceException;
import com.vkakarla.springboot.exceptionhandling.junits.entities.Person;
import com.vkakarla.springboot.exceptionhandling.junits.repository.PersonRepository;
import com.vkakarla.springboot.exceptionhandling.junits.service.IPersonService;

@Service
public class PersonService implements IPersonService {

	@Autowired
	private PersonRepository personRepository;

	public Person getPersonByPersonId(String personId) throws ServiceException {

		Person person = null;

		try {
			person = personRepository.getPersonByPersonId(personId);

			if (person == null) {
				throw new ServiceException(ErrorCode.SERVICE_002, new Object[] {personId});
			}

		} catch (ServiceException ex) {
			throw ex;
		}catch (Exception ex) {
			ServiceException px = new ServiceException(ErrorCode.SERVICE_004);
			
			throw px;
		}

		return person;
	}

	public Person createPerson(Person Person) throws ServiceException {
		return personRepository.save(Person);
	}

	@Override
	public Person getPersonBySSN(long ssn) {

		Person person = null;

		try {
			person = personRepository.getPersonBySSN(ssn);
			if (person == null) {
				throw new ServiceException(ErrorCode.SERVICE_002, new Object[] { ssn });
			}
		} catch (ServiceException ex) {
			throw ex;
		}

		catch (Exception e) {
			ServiceException px = new ServiceException(ErrorCode.SERVICE_004);
			throw px;
		}

		return person;
	}

}
