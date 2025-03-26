package com.example.TF.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.TF.entity.Movie_info_stillcut;
import com.example.TF.repository.Movie_info_stillcut_Repository;

@Repository
public class Movie_info_stillcutDAO {
	
	@Autowired
	Movie_info_stillcut_Repository stillcut_repository;
	
	// 사진 등록
	public Movie_info_stillcut stillcut_write(Movie_info_stillcut stillcut) {
		return stillcut_repository.save(stillcut);
	}
	
	// 목록
	public List<Movie_info_stillcut> stillcut_list(int moviecode, int startNum, int endNum) {
		return stillcut_repository.findByMoviecodeAndStartnumAndEndnum(moviecode, startNum, endNum);
	}
	
	// 총 사진 수
	public int get_count() {
		return (int)stillcut_repository.count();
	}
	
	// 영화별 사진 수
	public int get_count_moviecode(int moviecode) {
		return (int)stillcut_repository.count_moviecode(moviecode);
	}
	
	// 상세보기
	public Movie_info_stillcut stillcut_view(int movie_stillcut_code) {
		return stillcut_repository.findById(movie_stillcut_code).orElse(null);
	}
	
	// 수정
	public int stillcut_modify(Movie_info_stillcut stillcut) {
		// 1. 기존 데이터 가져오기
		Movie_info_stillcut stillcut_old = stillcut_repository.findById(stillcut.getMovie_stillcut_code()).orElse(null);
		int result = 0;
		if (stillcut_old != null) {
			Movie_info_stillcut stillcut_result = stillcut_repository.save(stillcut);
			if (stillcut_result != null) {
				result = 1;
			}
		}
		return result;
	}
	
	// 삭제
	public int stillcut_delete(int movie_stillcut_code) {
		// 1. 기존 데이터 가져오기
		Movie_info_stillcut stillcut = stillcut_repository.findById(movie_stillcut_code).orElse(null);
		int result = 0;
		if (stillcut != null) {
			// 2. 삭제하기
			stillcut_repository.delete(stillcut);
			// 3. 존재하는지 검사
			if (!stillcut_repository.existsById(movie_stillcut_code)) {
				result = 1;
			}
		}
		return result;
	}
}
