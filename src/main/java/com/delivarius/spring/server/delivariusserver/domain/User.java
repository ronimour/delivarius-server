package com.delivarius.spring.server.delivariusserver.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Persistable;

import com.delivarius.spring.server.delivariusserver.domain.utils.PersistableUtils;

@Entity
public class User implements Persistable<Long> {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(unique=true)
	private String login;

	@NotEmpty
	private String password;

	@NotEmpty
	private String firstName;

	@NotEmpty
	private String lastName;

	private String picture;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Address address;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Phone phone;

	@NotNull
	private LocalDateTime registrationDate;
	
	@NotNull
    @Enumerated(EnumType.STRING)
	private UserType type;
	
	private LocalDate birthDate;
	
	private String email;

	public User() {}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public Phone getPhone() {
		return phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean isNew() {
		return PersistableUtils.isNew(this);
	}

	@Override
	public boolean equals(Object obj) {
		return PersistableUtils.equalsPersistable(this, obj);		
	}
}
