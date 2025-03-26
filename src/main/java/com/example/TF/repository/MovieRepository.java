package com.example.TF.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.TF.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{
	 Movie findByTitle(String title);
}
