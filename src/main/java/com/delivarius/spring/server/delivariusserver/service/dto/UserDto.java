package com.delivarius.spring.server.delivariusserver.service.dto;

import javax.validation.constraints.NotNull;

public class UserDto implements DataTranferObject{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotNull
	private String login;
	
	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;

	private String picture;
	
	private String password;
	
	@NotNull
	private AddressDto address;
	
	@NotNull
	private PhoneDto phone;
	
	public UserDto() {}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AddressDto getAddress() {
		return address;
	}

	public void setAddress(AddressDto address) {
		this.address = address;
	}

	public PhoneDto getPhone() {
		return phone;
	}

	public void setPhone(PhoneDto phone) {
		this.phone = phone;
	}

}
