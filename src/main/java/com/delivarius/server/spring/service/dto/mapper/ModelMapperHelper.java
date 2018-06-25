package com.delivarius.server.spring.service.dto.mapper;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.reflections.Reflections;
import org.springframework.data.domain.Persistable;

import com.delivarius.server.spring.service.dto.DataTranferObject;
import com.delivarius.server.spring.service.dto.annotation.MapperFor;
import com.delivarius.server.spring.service.dto.mapper.exception.MapperConvertDtoException;

public class ModelMapperHelper {
	
	private static ModelMapperHelper singleton = null;
	
	private static Map<Class<?>, Class<?>> mappersDtoByClass = null;
	
	static{
		Reflections ref = new Reflections("com.delivarius.spring.server.delivariusserver.service.dto.mapper");
		mappersDtoByClass = new HashMap<>();
		for(Class<?> classMapperDto : ref.getTypesAnnotatedWith(MapperFor.class)) {
			MapperFor mapperFor = classMapperDto.getAnnotation(MapperFor.class);
			Class<?> classMapperDtoIsFor = mapperFor.classType();
			mappersDtoByClass.put(classMapperDtoIsFor, classMapperDto);
		}
	}

	private ModelMapperHelper() {}
	
	public synchronized static ModelMapperHelper getInstance() {
		if(singleton == null) {
			singleton = new ModelMapperHelper();
		}
		return singleton;
	}
	
	/**
	 * Convert the obj from Class definied by type.
	 * @param type class that will be used as a lookup for the suitable {@link MapperDto}
	 * @param ptb
	 * @return
	 * @throws MapperConvertDtoException
	 */
	public DataTranferObject convert(@NotNull Class<?> type, @NotNull Persistable<Long> ptb ) throws MapperConvertDtoException{
		
		try {
			MapperDto<?> mapperDto = getMapperDatoInstanceForClass(type);
			return mapperDto.convertToDto(ptb);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new MapperConvertDtoException("Could not convert persistable to dto");
		}
	}
	
	/**
	 * 
	 * @param type
	 * @param dto
	 * @return
	 * @throws MapperConvertDtoException
	 */
	public Persistable<Long> convert(@NotNull Class<?> type, @NotNull DataTranferObject dto) throws MapperConvertDtoException{
		try {
			MapperDto<?> mapperDto = getMapperDatoInstanceForClass(type);
			return mapperDto.convertToEntity(dto);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new MapperConvertDtoException("Could not convert dto to persistable");
		}
	}
	
	public MapperDto<?> getMapperDatoInstanceForClass(@NotNull Class<?> type) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		Class<?> classForMapperDto = mappersDtoByClass.get(type);
		if(classForMapperDto == null){
			throw new ClassNotFoundException("Mapper class not found for class "+type.getName());
		}
		
		MapperDto<?> instance = (MapperDto<?>) classForMapperDto.newInstance();
		return instance;
	}
	
}
