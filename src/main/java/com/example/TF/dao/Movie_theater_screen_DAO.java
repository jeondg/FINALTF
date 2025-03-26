package com.example.TF.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.TF.entity.Movie_theater_screen;
import com.example.TF.repository.Movie_theater_screen_repository;

@Repository
public class Movie_theater_screen_DAO {

	@Autowired
	Movie_theater_screen_repository ts_repository;
	
	// 글쓰기
	public int screen_write(Movie_theater_screen screen) {
		Movie_theater_screen screen_search = 
				ts_repository.findByScreen_numAndTheater_code(screen.getScreen_num(), screen.getTheater_code());
		int result = 0;
		if (screen_search != null) {
			result = 0;
		} else {
			result = 1;
			ts_repository.save(screen);
		}
		return result;
	}
	
	// 목록
	public List<Movie_theater_screen> screen_list(int theater_code, int startNum, int endNum) {
		return ts_repository.findByTheater_codeAndStartnumAndEndnum(
				theater_code, startNum, endNum);
	}
	
	// 테스트 목록
	public List<Movie_theater_screen> screen_list_test(int startNum, int endNum) {
		return ts_repository.findByStartnumAndEndnum(startNum, endNum);
	}
	
	// 총글수
	public int get_count() {
		return (int)ts_repository.count();
	}
	
	// 상세보기
	public Movie_theater_screen screen_view(int screen_num, int theater_code) {
		return ts_repository.findByScreen_numAndTheater_code(screen_num, theater_code);
	}
	
	// 수정
	public int screen_modify(Movie_theater_screen screen) {
		Movie_theater_screen screen_old = ts_repository.findByScreen_numAndTheater_code(screen.getScreen_num(), screen.getTheater_code());
		int result = 0;
		if (screen_old != null) {
			// 2. 수정 
			if (screen.getScreen_image1() == null || screen.getScreen_image1().equals("")) {
				screen.setScreen_image1(screen_old.getScreen_image1());
			}
			// 3. 저장
			ts_repository.delete(screen_old);
			Movie_theater_screen screen_result = ts_repository.save(screen);
			if (screen_result != null) {
				result = 1;
			}
		}
		return result;
	}
	
	// 삭제
	public int screen_delete(int screen_num, int theater_code) {
		Movie_theater_screen screen = ts_repository.findByScreen_numAndTheater_code(screen_num, theater_code);
		int result = 0;
		
		if (screen != null) {
			// 2. 삭제하기
			ts_repository.delete(screen);
			// 3. 존재하는지 검사
			if (ts_repository.findByScreen_numAndTheater_code(screen_num, theater_code) != null) {
				result = 0;
			} else {
				result = 1;
			}
		}
		return result;
	}
}
