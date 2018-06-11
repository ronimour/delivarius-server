package com.delivarius.spring.server.delivariusserver.service.dto.mapper;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Persistable;

import com.delivarius.spring.server.delivariusserver.domain.User;
import com.delivarius.spring.server.delivariusserver.service.dto.DataTranferObject;
import com.delivarius.spring.server.delivariusserver.service.dto.UserDto;
import com.delivarius.spring.server.delivariusserver.service.dto.annotation.MapperFor;
import com.delivarius.spring.server.delivariusserver.service.dto.mapper.exception.MapperConvertDtoException;

@MapperFor(classType = User.class)
public class UserMapper extends ModelMapper<User> {
	
	public UserMapper() {}

	@Override
	public DataTranferObject convertToDto(@NotNull Persistable<Long> entity) throws MapperConvertDtoException {
		return modelMapper.map(entity, UserDto.class);
	}

	@Override
	public Persistable<Long> convertToEntity(DataTranferObject dto) throws MapperConvertDtoException {
		return modelMapper.map(dto, User.class);
	}

}
