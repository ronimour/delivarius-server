package com.delivarius.server.spring.service.dto;

import javax.validation.constraints.NotEmpty;

public class LoginDto implements DataTranferObject {
	
	private static final long serialVersionUID = 1L;

	@NotEmpty
	private String login;
	
	@NotEmpty
	private String password;

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
	
}
