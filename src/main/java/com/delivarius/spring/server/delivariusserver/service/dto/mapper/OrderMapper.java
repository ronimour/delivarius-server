package com.delivarius.spring.server.delivariusserver.service.dto.mapper;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Persistable;

import com.delivarius.spring.server.delivariusserver.domain.Order;
import com.delivarius.spring.server.delivariusserver.service.dto.DataTranferObject;
import com.delivarius.spring.server.delivariusserver.service.dto.OrderDto;
import com.delivarius.spring.server.delivariusserver.service.dto.annotation.MapperFor;
import com.delivarius.spring.server.delivariusserver.service.dto.mapper.exception.MapperConvertDtoException;

@MapperFor(classType = Order.class)
public class OrderMapper extends ModelMapper<Order> {
	
	public OrderMapper() {}

	@Override
	public DataTranferObject convertToDto(@NotNull Persistable<Long> entity) throws MapperConvertDtoException {
		return modelMapper.map(entity, OrderDto.class);
	}

	@Override
	public Persistable<Long> convertToEntity(DataTranferObject dto) throws MapperConvertDtoException {
		return modelMapper.map(dto, Order.class);
	}

}
