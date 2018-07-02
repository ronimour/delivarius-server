package com.delivarius.server.spring.service.dto.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Persistable;

import com.delivarius.server.spring.domain.Product;
import com.delivarius.server.spring.domain.ProductStock;
import com.delivarius.server.spring.domain.Store;
import com.delivarius.server.spring.domain.helper.ProductHelper;
import com.delivarius.server.spring.service.dto.DataTransferObject;
import com.delivarius.server.spring.service.dto.ProductDto;
import com.delivarius.server.spring.service.dto.StoreDto;
import com.delivarius.server.spring.service.dto.annotation.MapperFor;
import com.delivarius.server.spring.service.dto.mapper.exception.MapperConvertDtoException;

@MapperFor(classType = Store.class)
public class StoreMapper extends ModelMapper<Store> {
	
	public StoreMapper() {}

	@Override
	public DataTransferObject convertToDto(@NotNull Persistable<Long> entity) throws MapperConvertDtoException {
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
			BigDecimal price = ProductHelper.getPriceAtStore(product, store);
			//TODO handle price null
			pDto.setPrice(price);
			productsDto.add(pDto);
		}
		storeDto.setProducts(productsDto);		
		
		return storeDto;
	}

	@Override
	public Persistable<Long> convertToEntity(DataTransferObject dto) throws MapperConvertDtoException {
		// TODO Auto-generated method stub
		return null;
	}

}
