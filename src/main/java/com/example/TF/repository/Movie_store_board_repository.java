package com.example.TF.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.TF.entity.Movie_store_board;

public interface Movie_store_board_repository extends JpaRepository<Movie_store_board, Integer> {
	
	@Query(value = "select * from (select rownum rn, tt.* from "
				 + "(select * from movie_store_board order by seq desc) tt) "
				 + "where rn>=:startNum and rn<=:endNum", nativeQuery = true)
	List<Movie_store_board> findByStartnumAndEndnum(
			@Param("startNum") int startNum, 
			@Param("endNum") int endNum);
}
