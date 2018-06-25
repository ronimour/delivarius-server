package com.delivarius.server.spring.service.dto;

import java.math.BigDecimal;

public class ProductDto implements DataTranferObject{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name;
	
	private String description;
	
	private String picture;
	
	private BigDecimal price;
	
	public ProductDto() {}

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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}



}
