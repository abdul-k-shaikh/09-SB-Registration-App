package com.abd.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abd.entities.CountryEntity;

public interface CountryRepository extends JpaRepository<CountryEntity, Serializable> {

}
