package com.abd.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abd.bindings.City;
import com.abd.entities.CityEntity;

public interface CityRepository extends JpaRepository<CityEntity, Serializable> {

	public List<CityEntity> findByStateId(Integer stateId);

}
