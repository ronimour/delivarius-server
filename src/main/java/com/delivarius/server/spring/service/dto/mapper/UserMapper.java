package com.delivarius.server.spring.service.dto.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Persistable;

import com.delivarius.server.spring.domain.User;
import com.delivarius.server.spring.service.dto.DataTransferObject;
import com.delivarius.server.spring.service.dto.UserDto;
import com.delivarius.server.spring.service.dto.annotation.MapperFor;
import com.delivarius.server.spring.service.dto.mapper.exception.MapperConvertDtoException;

@MapperFor(classType = User.class)
public class UserMapper extends ModelMapper<User> {
	
	public UserMapper() {}
	
	private final String PATTERN_DATE = "\\d{2}-\\d{2}-\\d{4}";

	@Override
	public DataTransferObject convertToDto(@NotNull Persistable<Long> entity) throws MapperConvertDtoException {
		UserDto userDto = modelMapper.map(entity, UserDto.class);
		User user = (User) entity;
		if(user.getBirthDate() != null)
			userDto.setBirthDate(user.getBirthDate().format(LOCAL_DATE_TIME_PATTERN_FORMATTER));
		return userDto;
	}

	@Override
	public Persistable<Long> convertToEntity(DataTransferObject dto) throws MapperConvertDtoException {
		User user = modelMapper.map(dto, User.class);
		UserDto userDto = (UserDto) dto;
		if(userDto.getBirthDate() != null) {
			String birthDate = normalizeDate(userDto.getBirthDate());
			try {
				if (birthDate.matches(PATTERN_DATE))
					user.setBirthDate(LocalDate.parse(birthDate, LOCAL_DATE_TIME_PATTERN_FORMATTER));
			} catch (DateTimeParseException e) {
				user.setBirthDate(null);
			}
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
