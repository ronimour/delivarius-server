package com.delivarius.spring.server.delivariusserver.service.dto.mapper;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Persistable;

import com.delivarius.spring.server.delivariusserver.domain.Product;
import com.delivarius.spring.server.delivariusserver.service.dto.DataTranferObject;
import com.delivarius.spring.server.delivariusserver.service.dto.ProductDto;
import com.delivarius.spring.server.delivariusserver.service.dto.annotation.MapperFor;
import com.delivarius.spring.server.delivariusserver.service.dto.mapper.exception.MapperConvertDtoException;

@MapperFor(classType = Product.class)
public class ProductMapper extends ModelMapper<Product> {
	
	public ProductMapper() {}

	@Override
	public DataTranferObject convertToDto(@NotNull Persistable<Long> entity) {
		return modelMapper.map(entity, ProductDto.class);
	}

	@Override
	public Persistable<Long> convertToEntity(DataTranferObject dto) throws MapperConvertDtoException {
		return modelMapper.map(dto, Product.class);
	}

}
