package com.javaSecurity.JWTAuthorize.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaSecurity.JWTAuthorize.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
