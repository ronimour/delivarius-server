package com.delivarius.server.spring.service.dto.mapper;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Persistable;

import com.delivarius.server.spring.domain.Address;
import com.delivarius.server.spring.service.dto.AddressDto;
import com.delivarius.server.spring.service.dto.DataTranferObject;
import com.delivarius.server.spring.service.dto.annotation.MapperFor;
import com.delivarius.server.spring.service.dto.mapper.exception.MapperConvertDtoException;

@MapperFor(classType = Address.class)
public class AddressMapper extends ModelMapper<Address> {
	
	public AddressMapper() {}

	@Override
	public DataTranferObject convertToDto(@NotNull Persistable<Long> entity) {
		AddressDto dto = new AddressDto();
		dto = modelMapper.map(entity, AddressDto.class);		
		return dto;
	}

	@Override
	public Persistable<Long> convertToEntity(DataTranferObject dto) throws MapperConvertDtoException {
		// TODO Auto-generated method stub
		return null;
	}

}
