package com.delivarius.spring.server.delivariusserver.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.domain.Persistable;

import com.delivarius.spring.server.delivariusserver.domain.utils.PersistableUtils;

@Entity
public class Address implements Persistable<Long> {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String city;
	
	private String state;
	
	@NotEmpty
	private String street;
	
	private String reference;
	
	private String zipCode;
	
	private Double latitude;
	
	private Double longitude;
	
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
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
