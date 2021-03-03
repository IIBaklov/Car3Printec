package com.ivo.cars.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivo.cars.model.Car;
import com.ivo.cars.model.Mark;


@Repository
public interface CarRepository extends JpaRepository<Car,Integer>{
	Car findByRegNum(String name);
}
