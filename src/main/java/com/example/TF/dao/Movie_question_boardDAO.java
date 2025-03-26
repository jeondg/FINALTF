package com.example.TF.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.TF.dto.Movie_question_boardDTO;
import com.example.TF.entity.Movie_question_board;
import com.example.TF.repository.Movie_question_boardRepository;

@Repository
public class Movie_question_boardDAO {
	@Autowired
	Movie_question_boardRepository movie_question_boardRepository;
	
	// 총글수
	public int get_count() {
		return (int)movie_question_boardRepository.count();
	}
	
	// 개별 글수
	public int get_count_question_id(String question_id) {
		return (int)movie_question_boardRepository.count_question_id(question_id);
	}
	
	// 전체 목록 보기
	public List<Movie_question_board> question_list(int startNum, int endNum){
		return movie_question_boardRepository.findByStartnumAndEndnum(startNum, endNum);
	}
	
	// 개별 목록 보기
	public List<Movie_question_board> question_list_question_id(String question_id, int startNum, int endNum){
		return movie_question_boardRepository.findByStartnumAndEndnum_question_id(question_id, startNum, endNum);
	}
	
	// 글 저장
	public Movie_question_board question_board_write(Movie_question_boardDTO dto) {
		return movie_question_boardRepository.save(dto.toEntity());
	}
	
	// 상세보기
	public Movie_question_board question_board_view(int seq) {
		return movie_question_boardRepository.findById(seq).orElse(null);
	}
	
	// 글 삭제
	public boolean question_board_delete(int seq) {
		Movie_question_board movie_question_board = movie_question_boardRepository.findById(seq).orElse(null);
		boolean result = false;
		if(movie_question_board != null) {
			movie_question_boardRepository.delete(movie_question_board);
			
			if(!movie_question_boardRepository.existsById(seq)) {
				result = true;
			}
		}
		return result;
	}
	
}
