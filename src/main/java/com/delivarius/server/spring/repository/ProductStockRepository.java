package com.delivarius.server.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delivarius.server.spring.domain.ProductStock;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
	
}
