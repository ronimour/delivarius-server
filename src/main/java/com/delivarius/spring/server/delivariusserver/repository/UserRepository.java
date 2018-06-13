package com.delivarius.spring.server.delivariusserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delivarius.spring.server.delivariusserver.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public List<User> findByLogin(String login);
}
