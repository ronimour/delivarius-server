package com.delivarius.server.spring.domain.helper;

import java.math.BigDecimal;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.delivarius.server.spring.domain.Product;
import com.delivarius.server.spring.domain.ProductStock;
import com.delivarius.server.spring.domain.Store;


public class ProductHelper {

	
	/**
	 * Get the price os some product at some store. 
	 * 
	 * @param product
	 * @param store
	 * @return the BigDecimal that represents the price of the product or null if there is no such product at the store
	 */
	public static BigDecimal getPriceAtStore(@NotNull  Product product, @NotNull Store store) {
		
		Optional<ProductStock> prodStock = store.getProductsStock()
				.stream()
				.filter( ps -> ps.getProduct().equals(product))
				.findFirst();
		
		return prodStock.isPresent() ? prodStock.get().getPrice() : null;
		
	}
}
