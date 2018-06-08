package com.delivarius.spring.server.delivariusserver.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delivarius.spring.server.delivariusserver.domain.Store;
import com.delivarius.spring.server.delivariusserver.repository.StoreRepository;

@RestController
@RequestMapping("/store")
@CrossOrigin
public class StoreResource {

	@Autowired
	private StoreRepository storeRepository;
	
	@GetMapping(path = "/all", produces= {"application/json"})
	public List<Store> getAll(){
		return storeRepository.findAll();
	}
}
