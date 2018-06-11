package com.delivarius.spring.server.delivariusserver.service.dto.mapper;

public abstract class ModelMapper<T> implements MapperDto<T> {
	
	protected org.modelmapper.ModelMapper modelMapper = new org.modelmapper.ModelMapper();
	
	protected ModelMapperHelper modelMapperHelper = ModelMapperHelper.getInstance();
	

}
