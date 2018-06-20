package com.delivarius.spring.server.delivariusserver.service.dto.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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
	
	private final String PATTERN_DATE = "\\d{2}-\\d{2}-\\d{4}";

	@Override
	public DataTranferObject convertToDto(@NotNull Persistable<Long> entity) throws MapperConvertDtoException {
		UserDto userDto = modelMapper.map(entity, UserDto.class);
		User user = (User) entity;
		if(user.getBirthDate() != null)
			userDto.setBirthDate(user.getBirthDate().format(LOCAL_DATE_TIME_PATTERN_FORMATTER));
		return userDto;
	}

	@Override
	public Persistable<Long> convertToEntity(DataTranferObject dto) throws MapperConvertDtoException {
		User user = modelMapper.map(dto, User.class);
		UserDto userDto = (UserDto) dto;
		String birthDate = normalizeDate(userDto.getBirthDate());
		
		try {
			if(birthDate.matches(PATTERN_DATE))
				user.setBirthDate(LocalDate.parse(birthDate, LOCAL_DATE_TIME_PATTERN_FORMATTER));
		} catch (DateTimeParseException e) {
			user.setBirthDate(null);
		}
		return user;
	}

	private String normalizeDate(String birthDate) {
		if(birthDate.contains("/")) {
			birthDate = birthDate.replaceAll("/", "-");
		}
		return birthDate;
	}

}
