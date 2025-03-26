package com.example.TF.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.TF.dto.Movie_reviewDTO;
import com.example.TF.entity.Movie_review;
import com.example.TF.repository.Movie_reviewRepository;

@Repository
public class Movie_reviewDAO {
	@Autowired
	Movie_reviewRepository movie_reviewRepository;
	
	// 목록 보기
	public List<Movie_review> review_list(int startNum, int endNum){
		return movie_reviewRepository.findByStartnumAndEndnum_logtime(startNum, endNum);
	}
	
	// 전체 리뷰수 조회
	public int getreviewcount() {
		return (int)movie_reviewRepository.count();
	}
	
    // 등급 포함된 DTO 반환 방식 (★추가된 부분)
	public List<Movie_reviewDTO> review_list_with_grade_paging(int moviecode, int start, int end) {
	    List<Object[]> rows = movie_reviewRepository.findReviewWithGradePaging(moviecode, start, end);
	    List<Movie_reviewDTO> result = new ArrayList<>();

	    for (Object[] row : rows) {
	        Movie_reviewDTO dto = new Movie_reviewDTO();
	        dto.setReviewcode(((Number) row[1]).intValue());
	        dto.setMoviecode(((Number) row[2]).intValue());
	        dto.setUser_id((String) row[3]);
	        dto.setReview_comment((String) row[4]);
	        dto.setRating(((Number) row[5]).intValue());
	        dto.setLogtime((java.sql.Timestamp) row[6]);
	        dto.setGrade((String) row[7]);
	        result.add(dto);
	    }
	    return result;
	}

	public List<Movie_reviewDTO> review_list_by_like(int moviecode, int start, int end) {
	    List<Object[]> rows = movie_reviewRepository.findReviewSortedByLike(moviecode, start, end);
	    List<Movie_reviewDTO> result = new ArrayList<>();

	    for (Object[] row : rows) {
	        Movie_reviewDTO dto = new Movie_reviewDTO();
	        dto.setReviewcode(((Number) row[1]).intValue());
	        dto.setMoviecode(((Number) row[2]).intValue());
	        dto.setUser_id((String) row[3]);
	        dto.setReview_comment((String) row[4]);
	        dto.setRating(((Number) row[5]).intValue());
	        dto.setLogtime((java.sql.Timestamp) row[6]);
	        dto.setGrade((String) row[7]);
	        result.add(dto);
	    }
	    return result;
	}
	
	// 리뷰저장
	public Movie_review insertreview(Movie_reviewDTO dto) {
		return movie_reviewRepository.save(dto.toEntity());
	}
	
	// 리뷰하나인지
	public boolean checkExists(int moviecode, String user_id) {
	    return movie_reviewRepository.countByMoviecodeAndUser_id(moviecode, user_id) > 0;
	}

	
	public Movie_review findByCode(int reviewcode) {
	    return movie_reviewRepository.findById(reviewcode).orElse(null);
	}

	public void save(Movie_review review) {
	    movie_reviewRepository.save(review);
	}

	public void delete(int reviewcode) {
	    movie_reviewRepository.deleteById(reviewcode);
	}
	
	// 리뷰수 카운트
	public int countByMoviecode(int moviecode) {
	    return movie_reviewRepository.countReviewsByMoviecode(moviecode);
	}
}
