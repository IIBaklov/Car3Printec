package com.ivo.cars.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ivo.cars.model.Model;


@Repository
public interface ModelRepository extends JpaRepository<Model,Integer>{
	Model findByModel(String model);

@Query(value="SELECT * FROM model m WHERE m.mark_id = :mark", nativeQuery = true)
List<Model> findModelByMark(@Param("mark") Integer mark );
}
