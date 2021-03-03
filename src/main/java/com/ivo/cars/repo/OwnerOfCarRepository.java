package com.ivo.cars.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivo.cars.model.OwnerOfCar;


@Repository
public interface OwnerOfCarRepository extends JpaRepository<OwnerOfCar,Integer>{
	
}
