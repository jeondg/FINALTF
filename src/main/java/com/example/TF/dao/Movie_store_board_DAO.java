package com.example.TF.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.TF.entity.Movie_store_board;
import com.example.TF.repository.Movie_store_board_repository;

@Repository
public class Movie_store_board_DAO {
	
	@Autowired
	Movie_store_board_repository store_repository;
	
	// 글쓰기
	public Movie_store_board store_board_write(Movie_store_board board) {
		return store_repository.save(board);
	}
	
	// 목록
	public List<Movie_store_board> store_board_list(int startNum, int endNum) {
		return store_repository.findByStartnumAndEndnum(startNum, endNum);
	}
	
	// 총글수
	public int get_count() {
		return (int)store_repository.count();
	}
	
	// 상세보기
	public Movie_store_board store_board_view(int seq) {
		return store_repository.findById(seq).orElse(null);
	}
	
	// 삭제
	public int store_board_delete(int seq) {
		// 1. 기존 데이터 가져오기
		Movie_store_board board = store_repository.findById(seq).orElse(null);
		int result = 0;
		
		if (board != null) {
			// 2. 삭제하기
			store_repository.delete(board);
			// 3. 존재하는지 검사
			if (!store_repository.existsById(seq)) {
				result = 1;
			}
		}
		return result;
	}
	
	// 수정
	public int store_board_modify(Movie_store_board board) {
		// 1. 기존 데이터 가져오기
		Movie_store_board board_old = store_repository.findById(board.getSeq()).orElse(null);
		int result = 0;
		if (board_old != null) {
			// 2. 수정
			board.setLogtime(board_old.getLogtime());
			// 3. 저장
			Movie_store_board board_result = store_repository.save(board);
			if (board_result != null) {
				result = 1;
			}
		}
		return result;
	}
}
