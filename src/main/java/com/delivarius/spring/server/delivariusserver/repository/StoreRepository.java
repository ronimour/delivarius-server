package com.delivarius.spring.server.delivariusserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delivarius.spring.server.delivariusserver.domain.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {

}
