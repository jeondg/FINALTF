package com.example.TF.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.TF.entity.Movie_user_qna_board;
import com.example.TF.repository.Movie_user_qna_board_repository;


@Repository
public class Movie_user_qna_board_DAO {

	@Autowired
	Movie_user_qna_board_repository qna_repository;
	
	// 전체 목록 보기
	public List<Movie_user_qna_board> qna_list(int startNum, int endNum) {
		return qna_repository.findByStartnumAndEndnum(startNum, endNum);
	}
	
	// 총글수
	public int get_count() {
		return (int)qna_repository.count();
	}
	
	// 항목별 글수
	public int get_count_section(String section) {
		return (int)qna_repository.count_section(section);
	}
	
	// 개별 목록 보기
	public List<Movie_user_qna_board> qna_list_section(String section, int startNum, int endNum) {
		return qna_repository.findByStartnumAndEndnum_section(section, startNum, endNum);
	}
	
	// 글쓰기
	public Movie_user_qna_board qna_board_write(Movie_user_qna_board board) {
		return qna_repository.save(board);
	}
	
	// 글 보기
	public Movie_user_qna_board qna_board_view(int seq) {
		return qna_repository.findById(seq).orElse(null);
	}
	
	// 글 수정
	public int qna_board_modify(Movie_user_qna_board board) {
		// 1. 기존 데이터 가져오기
		Movie_user_qna_board board_old = qna_repository.findById(board.getSeq()).orElse(null);
		int result = 0;
		if (board_old != null) {
			// 2. 수정
			board.setLogtime(board_old.getLogtime());
			// 3. 저장
			Movie_user_qna_board board_result = qna_repository.save(board);
			if (board_result != null) {
				result = 1;
			}
		}
		return result;
	}
	
	// 글 삭제
	public int qna_board_delete(int seq) {
		// 1. 기존 데이터 가져오기
		Movie_user_qna_board board = qna_repository.findById(seq).orElse(null);
		int result = 0;
		
		if (board != null) {
			// 2. 삭제하기
			qna_repository.delete(board);
			// 3. 존재하는지 검사
			if (!qna_repository.existsById(seq)) {
				result = 1;
			}
		}
		return result;
	}
}
