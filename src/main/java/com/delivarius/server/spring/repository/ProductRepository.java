package com.delivarius.server.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delivarius.server.spring.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
}
