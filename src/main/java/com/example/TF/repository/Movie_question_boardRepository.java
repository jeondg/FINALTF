package com.example.TF.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.TF.entity.Movie_question_board;

public interface Movie_question_boardRepository extends JpaRepository<Movie_question_board, Integer>{
	// 전체 리스트
	@Query(value = "select * from (select rownum rn, tt.* from "
			+ "(select * from movie_question_board order by seq desc) tt) "
			+ "where rn>=:startNum and rn<=:endNum", nativeQuery = true)
	List<Movie_question_board> findByStartnumAndEndnum(@Param("startNum") int startNum,
			@Param("endNum") int endNum);
	
	// 개인별 리스트
	@Query(value = "select * from (select rownum rn, tt.* from \r\n"
			+ "(select * from movie_question_board where question_id =:question_id order by seq desc) tt)\r\n"
			+ "where rn>=:startNum and rn<=:endNum", nativeQuery = true)
	List<Movie_question_board> findByStartnumAndEndnum_question_id(
			@Param("question_id") String question_id,
			@Param("startNum") int startNum,
			@Param("endNum") int endNum);
	
	// 개인별 글수 구하기
	@Query(value = "select count(*) as cnt from movie_question_board where question_id=:question_id", nativeQuery = true)
	int count_question_id(@Param("question_id") String question_id);
}
