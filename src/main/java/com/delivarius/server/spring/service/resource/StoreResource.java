package com.delivarius.server.spring.service.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delivarius.server.spring.domain.Store;
import com.delivarius.server.spring.repository.StoreRepository;
import com.delivarius.server.spring.service.dto.StoreDto;
import com.delivarius.server.spring.service.dto.mapper.exception.MapperConvertDtoException;

@RestController
@RequestMapping("/store")
@CrossOrigin
public class StoreResource extends AbstractResource{

	@Autowired
	private StoreRepository storeRepository;
	
	@GetMapping(path = "/all", produces= {"application/json"})
	public List<StoreDto> getAll(){
		List<Store> stores = storeRepository.findAll();
		List<StoreDto> storesDto = new ArrayList<>();
		try {
			for(Store store : stores) {
				StoreDto dto = (StoreDto) modelMapperHelper.convert(Store.class, store); 
				storesDto.add(dto);
			}
		}catch (MapperConvertDtoException e) {
			e.printStackTrace();
		}
		
		return storesDto;
	}
}
