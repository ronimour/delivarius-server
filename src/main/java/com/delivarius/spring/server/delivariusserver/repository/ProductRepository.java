package com.delivarius.spring.server.delivariusserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delivarius.spring.server.delivariusserver.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
}
