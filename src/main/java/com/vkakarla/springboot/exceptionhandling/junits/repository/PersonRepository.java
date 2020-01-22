package com.vkakarla.springboot.exceptionhandling.junits.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vkakarla.springboot.exceptionhandling.junits.dto.ServiceException;
import com.vkakarla.springboot.exceptionhandling.junits.entities.Person;

@Repository
public interface PersonRepository extends MongoRepository<Person, String> {
	
	@Query("{'SSN' : ?0 }")
	public Person getPersonBySSN(Long ssn);
	
	@Query("{'personId' : ?0 }")
	public Person getPersonByPersonId(String personId) throws ServiceException;
}
