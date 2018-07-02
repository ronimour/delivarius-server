package com.delivarius.server.spring.service.dto;

public class PhoneDto implements DataTransferObject{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String number;
	
	private Boolean whatsapp;
	
	private Boolean celphone;
	
	public void setId(Long id) {
		this.id = id;
	}

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

}
