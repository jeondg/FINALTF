package com.example.TF.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.TF.entity.Movie_admin_board;
import com.example.TF.repository.Movie_admin_board_repository;

@Repository
public class Movie_admin_board_DAO {

	@Autowired
	Movie_admin_board_repository admin_repository;
	
	// 목록 보기
	public List<Movie_admin_board> admin_board_list(int startNum, int endNum) {
		return admin_repository.findByStartnumAndEndnum(startNum, endNum);
	}
	
	// 총글수
	public int get_count() {
		return (int)admin_repository.count();
	}
	
	// 글쓰기
	public Movie_admin_board admin_board_write(Movie_admin_board board) {
		return admin_repository.save(board);
	}
	
	// 상세보기 
	public Movie_admin_board admin_board_view(int seq) {
		return admin_repository.findById(seq).orElse(null);
	}
	
	// 글 수정
	public int admin_board_modify(Movie_admin_board board) {
		// 1. 기존 데이터 가져오기
		Movie_admin_board board_old = admin_repository.findById(board.getSeq()).orElse(null);
		int result = 0;
		if (board_old != null) {
			// 2. 수정
			board.setLogtime(board_old.getLogtime());
			// 3. 저장
			Movie_admin_board board_result = admin_repository.save(board);
			if (board_result != null) {
				result = 1;
			}
		}
		return result;
	}
	
	// 글 삭제
	public int admin_board_delete(int seq) {
		// 1. 기존 데이터 가져오기
		Movie_admin_board board = admin_repository.findById(seq).orElse(null);
		int result = 0;
		
		if (board != null) {
			// 2. 삭제하기
			admin_repository.delete(board);
			// 3. 존재하는지 검사
			if (!admin_repository.existsById(seq)) {
				result = 1;
			}
		}
		return result;
	}
}
