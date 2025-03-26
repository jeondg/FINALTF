package com.example.TF.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.TF.entity.Movie_theater_screen;

public interface Movie_theater_screen_repository extends JpaRepository<Movie_theater_screen, Integer> {
	
	// 전체 리스트
	@Query(value = "select * from (select rownum rn, tt.* from "
			+ "(select * from movie_theater_screen"
			+ " where theater_code=:theater_code order by screen_num desc) tt) "
			+ "where rn>=:startNum and rn<=:endNum", nativeQuery = true)
	List<Movie_theater_screen> findByTheater_codeAndStartnumAndEndnum(
			@Param("theater_code") int theater_code,
			@Param("startNum") int startNum,
			@Param("endNum") int endNum);
	
	// 테스트용 리스트
	@Query(value = "select * from (select rownum rn, tt.* from "
			+ "(select * from movie_theater_screen"
			+ " order by screen_num desc) tt) "
			+ "where rn>=:startNum and rn<=:endNum", nativeQuery = true)
	List<Movie_theater_screen> findByStartnumAndEndnum(
			@Param("startNum") int startNum,
			@Param("endNum") int endNum);	
	
	// 상영관 하나
	@Query(value = "select * from movie_theater_screen"
			+ " where screen_num=:screen_num and theater_code=:theater_code", nativeQuery = true)
	Movie_theater_screen findByScreen_numAndTheater_code(
			@Param("screen_num") int screen_num,
			@Param("theater_code") int theater_code);
}

