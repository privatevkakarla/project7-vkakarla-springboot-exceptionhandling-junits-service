package com.vkakarla.springboot.exceptionhandling.junits.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vkakarla.springboot.exceptionhandling.junits.dto.ServiceException;
import com.vkakarla.springboot.exceptionhandling.junits.entities.Person;
import com.vkakarla.springboot.exceptionhandling.junits.serviceImpl.PersonService;
import com.vkakarla.springboot.exceptionhandling.junits.utils.PersonRequestValidationUtil;

@RestController
public class PersonController {

	@Autowired
	private PersonService personService;

	@Autowired
	private PersonRequestValidationUtil validationUtil;

	@GetMapping(value = "/person-management/person/{personId}")
	public ResponseEntity<Object> getPerson(@PathVariable("personId") String personId)
			throws ServiceException {

		validationUtil.verifyPersonId(personId);
		Person person = personService.getPersonByPersonId(personId);
		return new ResponseEntity<Object>(person, HttpStatus.OK);

	}

	@GetMapping(value = "/person-management/{ssn}")
	public ResponseEntity<Object> getPersonBySSN(@PathVariable("ssn") long ssn) throws ServiceException {

		validationUtil.verifyPersonSSN(ssn);
		Person person = personService.getPersonBySSN(ssn);
		return new ResponseEntity<Object>(person, HttpStatus.OK);

	}

	@PostMapping("/person-management/person")
	public ResponseEntity<Object> createPerson(@RequestBody  Person person) {

		personService.createPerson(person);
		return new ResponseEntity<Object>(person, HttpStatus.OK);

	}
	
	

}
