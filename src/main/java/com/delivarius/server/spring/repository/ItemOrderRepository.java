package com.delivarius.server.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delivarius.server.spring.domain.ItemOrder;

public interface ItemOrderRepository extends JpaRepository<ItemOrder, Long> {
	
}
