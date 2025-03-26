package com.example.TF.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.TF.entity.Theater;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long>{
	java.util.List<Theater> findByLocation(String location);
}
