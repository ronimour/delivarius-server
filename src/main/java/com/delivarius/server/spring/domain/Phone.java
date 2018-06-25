package com.delivarius.server.spring.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.domain.Persistable;

import com.delivarius.server.spring.domain.utils.PersistableUtils;

@Entity
public class Phone implements Persistable<Long> {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String number;
	
	private Boolean whatsapp;
	
	private Boolean celphone;
	
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Boolean getWhatsapp() {
		return whatsapp;
	}

	public void setWhatsapp(Boolean whatsapp) {
		this.whatsapp = whatsapp;
	}

	public Boolean getCelphone() {
		return celphone;
	}

	public void setCelphone(Boolean celphone) {
		this.celphone = celphone;
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
