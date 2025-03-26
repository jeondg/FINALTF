package com.example.TF.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.TF.entity.Screen_seat;

public interface Screen_seat_repository extends JpaRepository<Screen_seat, Integer> {
	
	@Query(value = "select * from screen_seat"
			+ " where screen_num=:screen_num and theater_code=:theater_code "
			+ "order by y_index, x_index",
			nativeQuery = true)
	List<Screen_seat> findByScreen_numAndTheater_code(@Param("screen_num") int screen_num,
			@Param("theater_code") int theater_code);
	
	@Modifying
	@Query(value = "delete from screen_seat where screen_num=:sreen_num"
			+ " and theater_code=:theater_code", nativeQuery = true)
	void deleteByScreen_numAndTheater_code(@Param("sreen_num") int screen_num,
			@Param("theater_code") int theater_code);
}

