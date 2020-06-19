package com.vkakarla.springboot.exceptionhandling.junits.entities;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Order(Ordered.HIGHEST_PRECEDENCE)
@Document(collection="person-collection")
public class Person {

	@Id
	private String personId;

	private String firstName;
	
	private String lastName;
	
	private String dateOfBirth;

	private Long SSN;
	
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