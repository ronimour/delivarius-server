package com.delivarius.server.spring.service.resource;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.delivarius.server.spring.domain.exception.EntityNotFoundException;
import com.delivarius.server.spring.service.dto.mapper.ModelMapperHelper;
import com.delivarius.server.spring.service.dto.mapper.exception.MapperConvertDtoException;

public abstract class AbstractResource {

	protected final ModelMapperHelper modelMapperHelper = ModelMapperHelper.getInstance();

	@ExceptionHandler(Exception.class)
	public void handleExcetion(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	@ExceptionHandler(MapperConvertDtoException.class)
	public void handleMapperConvertDtoException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "parse error");
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public void handleEntityNotFoundException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "entity no found");
	}
}
