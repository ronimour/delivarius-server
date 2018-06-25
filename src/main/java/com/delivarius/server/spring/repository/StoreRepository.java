package com.delivarius.server.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delivarius.server.spring.domain.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {

}
