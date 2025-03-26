package com.example.TF.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.TF.entity.Movie_theater;

public interface Movie_theater_repository extends JpaRepository<Movie_theater, Integer> {
	
	@Query(value = "select * from (select rownum rn, tt.* from "
			+ "(select * from movie_theater order by seq desc) tt) "
			+ "where rn>=:startNum and rn<=:endNum", nativeQuery = true)
	List<Movie_theater> findByStartnumAndEndnum(@Param("startNum") int startNum,
			@Param("endNum") int endNum);
}
