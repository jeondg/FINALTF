package com.example.TF.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.TF.entity.Movie_user_qna_board;


public interface Movie_user_qna_board_repository extends JpaRepository<Movie_user_qna_board, Integer> {
	
	// 전체 리스트
	@Query(value = "select * from (select rownum rn, tt.* from "
			+ "(select * from movie_user_qna_board order by seq desc) tt) "
			+ "where rn>=:startNum and rn<=:endNum", nativeQuery = true)
	List<Movie_user_qna_board> findByStartnumAndEndnum(@Param("startNum") int startNum,
			@Param("endNum") int endNum);
	
	// 항목별 리스트
	@Query(value = "select * from (select rownum rn, tt.* from "
			+ "(select * from movie_user_qna_board where section=:section order by seq desc) tt) "
			+ "where rn>=:startNum and rn<=:endNum", nativeQuery = true)
	List<Movie_user_qna_board> findByStartnumAndEndnum_section(
			@Param("section") String section,
			@Param("startNum") int startNum,
			@Param("endNum") int endNum);
	
	// 항목별 글수 구하기
	@Query(value = "select count(*) as cnt from movie_user_qna_board"
			+ " where section=:section", nativeQuery = true)
	int count_section(@Param("section") String section);
}
