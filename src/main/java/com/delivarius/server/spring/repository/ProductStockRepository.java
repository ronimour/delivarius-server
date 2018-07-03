package com.delivarius.server.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.delivarius.server.spring.domain.Product;
import com.delivarius.server.spring.domain.ProductStock;
import com.delivarius.server.spring.domain.Store;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
	
	@Query("SELECT p FROM ProductStock p WHERE p.store.id = :storeId AND p.product.id = :productId LIMIT 1")
	public ProductStock findByStoreAndProdcut(Long storeId, Long productId);
	
	public List<ProductStock> findByStoreAndProduct(Store store, Product product);
	
}
