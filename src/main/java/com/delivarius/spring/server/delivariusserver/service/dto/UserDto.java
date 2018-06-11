package com.delivarius.spring.server.delivariusserver.service.dto;

public class UserDto implements DataTranferObject{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String login;
	
	private String firstName;
	
	private String lastName;

	private String picture;
	
	private AddressDto address;
	
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
