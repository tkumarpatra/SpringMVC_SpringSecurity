package com.fsd.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsd.cts.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}