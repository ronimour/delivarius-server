package com.delivarius.server.spring.service.resource;

import com.delivarius.server.spring.service.dto.mapper.ModelMapperHelper;

public abstract class AbstractResource {

	protected final ModelMapperHelper modelMapperHelper = ModelMapperHelper.getInstance();
}
