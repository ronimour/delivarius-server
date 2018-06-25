package com.delivarius.server.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delivarius.server.spring.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public List<User> findByLogin(String login);
}
