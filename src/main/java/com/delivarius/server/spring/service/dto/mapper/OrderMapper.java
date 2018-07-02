package com.delivarius.server.spring.service.dto.mapper;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Persistable;

import com.delivarius.server.spring.domain.Order;
import com.delivarius.server.spring.service.dto.DataTransferObject;
import com.delivarius.server.spring.service.dto.OrderDto;
import com.delivarius.server.spring.service.dto.annotation.MapperFor;
import com.delivarius.server.spring.service.dto.mapper.exception.MapperConvertDtoException;

@MapperFor(classType = Order.class)
public class OrderMapper extends ModelMapper<Order> {
	
	public OrderMapper() {}

	@Override
	public DataTransferObject convertToDto(@NotNull Persistable<Long> entity) throws MapperConvertDtoException {
		return modelMapper.map(entity, OrderDto.class);
	}

	@Override
	public Persistable<Long> convertToEntity(DataTransferObject dto) throws MapperConvertDtoException {
		return modelMapper.map(dto, Order.class);
	}

}
