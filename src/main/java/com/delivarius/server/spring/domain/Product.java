package com.delivarius.server.spring.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.domain.Persistable;

import com.delivarius.server.spring.domain.utils.PersistableUtils;

@Entity
public class Product implements Persistable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String name;

	@NotEmpty
	private String description;
	
	private String picture;
	
	public Product() {}

	@Override
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
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
