package com.example.TF.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.TF.entity.Movie_info_stillcut;

public interface Movie_info_stillcut_Repository extends JpaRepository<Movie_info_stillcut, Integer> {
	@Query(value = "select * from (select rownum rn, tt.* from "
			+ "(select * from movie_info_stillcut where moviecode=:moviecode order by movie_stillcut_code desc) tt) "
			+ "where rn>=:startNum and rn<=:endNum", nativeQuery = true)
	List<Movie_info_stillcut> findByMoviecodeAndStartnumAndEndnum(
			@Param("moviecode") int moviecode,
			@Param("startNum") int startNum,
			@Param("endNum") int endNum);
	
	// 영화별 사진 수
	@Query(value="select count(*) as cnt from movie_info_stillcut where moviecode=:moviecode", nativeQuery = true)
	int count_moviecode(@Param("moviecode") int moviecode);
}
