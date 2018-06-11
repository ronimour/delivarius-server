package com.delivarius.spring.server.delivariusserver.service.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Persistable;

import com.delivarius.spring.server.delivariusserver.domain.Product;
import com.delivarius.spring.server.delivariusserver.domain.ProductStock;
import com.delivarius.spring.server.delivariusserver.domain.Store;
import com.delivarius.spring.server.delivariusserver.service.dto.DataTranferObject;
import com.delivarius.spring.server.delivariusserver.service.dto.ProductDto;
import com.delivarius.spring.server.delivariusserver.service.dto.StoreDto;
import com.delivarius.spring.server.delivariusserver.service.dto.annotation.MapperFor;
import com.delivarius.spring.server.delivariusserver.service.dto.mapper.exception.MapperConvertDtoException;

@MapperFor(classType = Store.class)
public class StoreMapper extends ModelMapper<Store> {
	
	public StoreMapper() {}

	@Override
	public DataTranferObject convertToDto(@NotNull Persistable<Long> entity) throws MapperConvertDtoException {
		StoreDto storeDto = new StoreDto();
		storeDto = modelMapper.map(entity, StoreDto.class);
		Store store = (Store) entity;
		List<Product> products = store.getProductsStock()
				.stream()
				.map(ProductStock::getProduct)
				.collect(Collectors.toList());
		
		List<ProductDto> productsDto = new ArrayList<>();
		for(Product product : products) {
			ProductDto pDto = (ProductDto) modelMapperHelper.convert(Product.class, product);
			productsDto.add(pDto);
		}
		storeDto.setProducts(productsDto);		
		
		return storeDto;
	}

	@Override
	public Persistable<Long> convertToEntity(DataTranferObject dto) throws MapperConvertDtoException {
		// TODO Auto-generated method stub
		return null;
	}

}
