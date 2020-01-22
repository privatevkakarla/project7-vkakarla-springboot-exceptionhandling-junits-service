package com.vkakarla.springboot.exceptionhandling.junits.entities;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Order(Ordered.HIGHEST_PRECEDENCE)
@Document(collection="person-collection")
public class Person {

	@Id
	@NotEmpty(message = "personId must not be empty or null")
	private String personId;

	@NotEmpty(message = "firstName must not be empty or null")
	private String firstName;
	
	@NotEmpty(message = "lastName must not be empty or null")
	private String lastName;
	
	@NotEmpty(message = "dateOfBirth must not be empty or null")
	private String dateOfBirth;

	@NotNull(message = "ssn  must not be empty")
	private Long SSN;
	
	@NotBlank(message = "email must not be empty or null")
	@Email(message = "email should be proper format")
	private String email;
	

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public Long getSSN() {
		return SSN;
	}

	public void setSSN(Long sSN) {
		SSN = sSN;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

}