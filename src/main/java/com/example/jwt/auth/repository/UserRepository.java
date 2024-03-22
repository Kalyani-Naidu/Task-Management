package com.example.jwt.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jwt.auth.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long>{

	Optional<Users> findByEmail(String email);

}
