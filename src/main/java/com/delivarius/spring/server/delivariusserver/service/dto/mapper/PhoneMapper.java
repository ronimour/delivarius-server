package com.delivarius.spring.server.delivariusserver.service.dto.mapper;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Persistable;

import com.delivarius.spring.server.delivariusserver.domain.Phone;
import com.delivarius.spring.server.delivariusserver.service.dto.PhoneDto;
import com.delivarius.spring.server.delivariusserver.service.dto.DataTranferObject;
import com.delivarius.spring.server.delivariusserver.service.dto.annotation.MapperFor;
import com.delivarius.spring.server.delivariusserver.service.dto.mapper.exception.MapperConvertDtoException;

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
