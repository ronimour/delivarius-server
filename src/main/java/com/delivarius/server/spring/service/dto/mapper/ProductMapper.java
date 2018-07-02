package com.delivarius.server.spring.service.dto.mapper;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Persistable;

import com.delivarius.server.spring.domain.Product;
import com.delivarius.server.spring.service.dto.DataTransferObject;
import com.delivarius.server.spring.service.dto.ProductDto;
import com.delivarius.server.spring.service.dto.annotation.MapperFor;
import com.delivarius.server.spring.service.dto.mapper.exception.MapperConvertDtoException;

@MapperFor(classType = Product.class)
public class ProductMapper extends ModelMapper<Product> {
	
	public ProductMapper() {}

	@Override
	public DataTransferObject convertToDto(@NotNull Persistable<Long> entity) {
		return modelMapper.map(entity, ProductDto.class);
	}

	@Override
	public Persistable<Long> convertToEntity(DataTransferObject dto) throws MapperConvertDtoException {
		return modelMapper.map(dto, Product.class);
	}

}
