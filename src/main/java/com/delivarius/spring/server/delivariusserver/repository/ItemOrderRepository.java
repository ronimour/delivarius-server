package com.delivarius.spring.server.delivariusserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delivarius.spring.server.delivariusserver.domain.ItemOrder;

public interface ItemOrderRepository extends JpaRepository<ItemOrder, Long> {
	
}
