package com.vkakarla.springboot.exceptionhandling.junits;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vkakarla.springboot.exceptionhandling.junits.application.ExceptionHandlingAndJunitsApplication;
import com.vkakarla.springboot.exceptionhandling.junits.controller.PersonController;
import com.vkakarla.springboot.exceptionhandling.junits.entities.Person;
import com.vkakarla.springboot.exceptionhandling.junits.exception.ServiceException;
import com.vkakarla.springboot.exceptionhandling.junits.repository.PersonRepository;
import com.vkakarla.springboot.exceptionhandling.junits.serviceImpl.PersonService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExceptionHandlingAndJunitsApplication.class)
@TestPropertySource(locations = "classpath:application-mock.properties")
public class  PersonControllerTest {


	@Autowired 
	private PersonController personController;

	@Autowired
	@InjectMocks
	private PersonService personService;

	@MockBean
	private PersonRepository personRepository;

	@Autowired 
	ObjectMapper objectMapper;

	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
	}



	@Test 
	public void given_ValidSSN_When_Calling_PersonBySSN_Then_Return_PersonDetasils()
			throws IOException {

		try {
			Person personresponse = null;

			String personString = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("testdata/personDetailsBySSNResponse.json"), "UTF-8");
			personresponse = objectMapper.readValue(personString, new TypeReference<Person>() {});

			when(personRepository.getPersonBySSN((long) 12345)).thenReturn(personresponse);

			ResponseEntity<Object> response = personController.getPersonBySSN(12345);
			assertNotNull(response);
			assertEquals(HttpStatus.OK, response.getStatusCode());

		} catch (ServiceException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	@Test public void given_ValidPersonID_When_Calling_PersonByPersonID_Then_Return_PersonDetasils()
			throws IOException {

		try {
			Person personresponse = null;
			

			Person personActualResponse = null;

			String personString = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("testdata/personDetailsBySSNResponse.json"),"UTF-8");
			personresponse = objectMapper.readValue(personString, new TypeReference<Person>() {});

			when(personRepository.getPersonByPersonId("12345")).thenReturn(personresponse);

			ResponseEntity<Object> response = personController.getPerson("12345");

			
			//personActualResponse = (Person) serviceResponse.getData();

			assertNotNull(response);
			assertEquals(HttpStatus.OK, response.getStatusCode());

			assertEquals("12345", personActualResponse.getPersonId());
			assertEquals("Martin@gmail.com", personActualResponse.getEmail());

		} catch (ServiceException e) { // TODO Auto-generated catch block
			fail();
			e.printStackTrace();
		}

	}

	
	@Test 
	public void given_InValidSSN_When_Calling_PersonBySSN_Then_Return_Exception()
			throws IOException {

		try {

			ResponseEntity<Object> response = personController.getPersonBySSN(0);
			fail();

		} catch (ServiceException ex) { 
			assertEquals("SERVICE_001",ex.getErrorCode().name());
		}

	}
	

}
