package com.delivarius.server.spring.service.dto.mapper;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Persistable;

import com.delivarius.server.spring.domain.Phone;
import com.delivarius.server.spring.service.dto.DataTranferObject;
import com.delivarius.server.spring.service.dto.PhoneDto;
import com.delivarius.server.spring.service.dto.annotation.MapperFor;
import com.delivarius.server.spring.service.dto.mapper.exception.MapperConvertDtoException;

@MapperFor(classType = Phone.class)
public class PhoneMapper extends ModelMapper<Phone> {
	
	public PhoneMapper() {}

	@Override
	public DataTranferObject convertToDto(@NotNull Persistable<Long> entity) {
		PhoneDto dto = new PhoneDto();
		dto = modelMapper.map(entity, PhoneDto.class);		
		return dto;
	}

	@Override
	public Persistable<Long> convertToEntity(DataTranferObject dto) throws MapperConvertDtoException {
		// TODO Auto-generated method stub
		return null;
	}

}
