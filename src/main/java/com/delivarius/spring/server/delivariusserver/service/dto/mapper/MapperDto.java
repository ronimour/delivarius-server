package com.delivarius.spring.server.delivariusserver.service.dto.mapper;

import org.springframework.data.domain.Persistable;

import com.delivarius.spring.server.delivariusserver.service.dto.DataTranferObject;
import com.delivarius.spring.server.delivariusserver.service.dto.mapper.exception.MapperConvertDtoException;

public interface MapperDto<T> {
	
	/**
	 * Convert an entity object {@link Persistable<Long>} to a dto object {@link DataTranferObject} of class T
	 * @param entity
	 * @return
	 * @throws MapperConvertDtoException
	 */
	public DataTranferObject convertToDto(Persistable<Long> entity) throws MapperConvertDtoException;
	
	/**
	 * Convert a dto object {@link DataTranferObject} to an entity object {@link Persistable<Long>} of class T
	 * @param dto
	 * @return
	 * @throws MapperConvertDtoException
	 */
	public Persistable<Long> convertToEntity(DataTranferObject dto) throws MapperConvertDtoException;
}
