package com.example.TF.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.TF.entity.Movie_theater;
import com.example.TF.repository.Movie_theater_repository;

@Repository
public class Movie_theater_DAO {
	
	@Autowired
	Movie_theater_repository theater_repository;
	
	// 글쓰기
	public Movie_theater theater_write(Movie_theater theater) {
		return theater_repository.save(theater);
	}
	
	// 목록
	public List<Movie_theater> theater_list(int startNum, int endNum) {
		return theater_repository.findByStartnumAndEndnum(startNum, endNum);
	}
	
	// 총글수
	public int get_count() {
		return (int)theater_repository.count();
	}
	
	// 상세보기
	public Movie_theater theater_view(int seq) {
		return theater_repository.findById(seq).orElse(null);
	}
	
	// 삭제
	public int theater_delete(int seq) {
		// 1. 기존 데이터 가져오기
		Movie_theater theater = theater_repository.findById(seq).orElse(null);
		int result = 0;
		
		if (theater != null) {
			// 2. 삭제하기
			theater_repository.delete(theater);
			// 3. 존재하는지 검사
			if (!theater_repository.existsById(seq)) {
				result = 1;
			}
		}
		return result;
	}
	
	// 수정
	public int theater_modify(Movie_theater theater) {
		// 1. 기존 데이터 가져오기
		Movie_theater theater_old = theater_repository.findById(theater.getSeq()).orElse(null);
		int result = 0;
		if (theater_old != null) {
			// 2. 수정
			if (theater.getTheater_image1() == null || theater.getTheater_image1().equals("")) {
				theater.setTheater_image1(theater_old.getTheater_image1());
			}
			// 3. 저장
			Movie_theater theater_result = theater_repository.save(theater);
			if (theater_result != null) {
				result = 1;
			}
		}
		return result;
	}	
}
