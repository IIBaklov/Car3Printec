package com.ivo.cars.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivo.cars.model.Mark;


@Repository
public interface MarkRepository extends JpaRepository<Mark,Integer>{
	Mark findByMarkName(String name);
}
