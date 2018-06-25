package com.delivarius.server.spring.service.dto;

import java.time.LocalDateTime;

import com.delivarius.server.spring.domain.StatusOrder;

public class HistoryStatusOrderDto implements DataTranferObject{
	
	private static final long serialVersionUID = 1L;

	private Long id;

	private StatusOrder status;
	
	private LocalDateTime registrationDate;
	
	private UserDto userDto;
	
	public HistoryStatusOrderDto() {}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StatusOrder getStatus() {
		return status;
	}

	public void setStatus(StatusOrder status) {
		this.status = status;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}
	
}
