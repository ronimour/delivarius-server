package com.delivarius.server.spring.service.dto.mapper;

import org.springframework.data.domain.Persistable;

import com.delivarius.server.spring.service.dto.DataTransferObject;
import com.delivarius.server.spring.service.dto.mapper.exception.MapperConvertDtoException;

public interface MapperDto<T> {
	
	/**
	 * Convert an entity object {@link Persistable<Long>} to a dto object {@link DataTransferObject} of class T
	 * @param entity
	 * @return
	 * @throws MapperConvertDtoException
	 */
	public DataTransferObject convertToDto(Persistable<Long> entity) throws MapperConvertDtoException;
	
	/**
	 * Convert a dto object {@link DataTransferObject} to an entity object {@link Persistable<Long>} of class T
	 * @param dto
	 * @return
	 * @throws MapperConvertDtoException
	 */
	public Persistable<Long> convertToEntity(DataTransferObject dto) throws MapperConvertDtoException;
}
