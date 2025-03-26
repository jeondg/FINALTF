package com.example.TF.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.TF.entity.Movie_admin_board;

public interface Movie_admin_board_repository extends JpaRepository<Movie_admin_board, Integer> {
	@Query(value = "select * from (select rownum rn, tt.* from "
			+ "(select * from movie_admin_board order by seq desc) tt) "
			+ "where rn>=:startNum and rn<=:endNum", nativeQuery = true)
	List<Movie_admin_board> findByStartnumAndEndnum(@Param("startNum") int startNum,
			@Param("endNum") int endNum);
}
