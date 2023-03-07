package com.abd.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abd.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Serializable> {

	public UserEntity findByUserEmail(String userEmail);
}
