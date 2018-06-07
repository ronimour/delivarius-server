package com.delivarius.spring.server.delivariusserver.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaudacaoResource {
	
	@RequestMapping("/saudacao")
	public String saudacao() {
		return "Hello world!!!";
	}
}
