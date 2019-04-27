package com.fsd.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsd.cts.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
