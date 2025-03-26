package com.example.TF.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.TF.entity.Movie_info;
import com.example.TF.repository.Movie_infoRepository;

@Repository
public class Movie_infoDAO {
	@Autowired
	Movie_infoRepository movie_infoRepository;
	// 총영화수
	public int get_count() {
		return (int)movie_infoRepository.count();
	}
	// 장르별 영화수
	public int get_count_genre(String genre) {
		return (int)movie_infoRepository.count_genre(genre);
	}
	// 국내 영화수
	public int get_country() {
		return (int)movie_infoRepository.count_country();
	}
	// 해외 영화수
	public int get_foreign() {
		return (int)movie_infoRepository.count_foreign();
	}
	
	// 영화번호순
	public List<Movie_info> moviecode_list(int startNum, int endNum) {
		return movie_infoRepository.findByStartnumAndEndnum_moviecode(startNum, endNum);
	}
	
	// 장르별 박스오피스순
	public List<Movie_info> genrerank_list(String genre, int startNum, int endNum){
		return movie_infoRepository.findByStartnumAndEndnum_genrerank(genre, startNum, endNum);
	}
	// 국내 박스오피스순
	public List<Movie_info> countryrank_list(int startNum, int endNum){
		return movie_infoRepository.findByStartnumAndEndnum_countryrank(startNum, endNum);
	}
	// 해외 박스오피스순
	public List<Movie_info> foreignrank_list(int startNum, int endNum){
		return movie_infoRepository.findByStartnumAndEndnum_foreignrank(startNum, endNum);
	}
	
	// 박스오피스 순
	public List<Movie_info> boxoffice_list(int startNum, int endNum){
		return movie_infoRepository.findByStartnumAndEndnum_yesterday(startNum, endNum);
	}
	// 누적 관객순
	public List<Movie_info> total_list(int startNum, int endNum){
		return movie_infoRepository.findByStartnumAndEndnum_total(startNum, endNum);
	}	
	// 개봉일 순
	public List<Movie_info> releasedate_list(int startNum, int endNum){
		return movie_infoRepository.findByStartnumAndEndnum_releasedate(startNum, endNum);
	}	
	// 현재 상영작
	public List<Movie_info> nowshowing_list(int startNum, int endNum){
		return movie_infoRepository.findByStartNumAndEndnum_nowshowing(startNum, endNum);
	}	
	// 개봉 예정작
	public List<Movie_info> willshow_list(int startNum, int endNum){
		return movie_infoRepository.findByStartNumAndEndnum_willshow(startNum, endNum);
	}	
	
	// 영화상세정보
	public Movie_info movie_info_view(int moviecode) {
		return movie_infoRepository.findById(moviecode).orElse(null);
	}
	
	// 영화 등록
	public Movie_info movie_info_write(Movie_info movie_info) {
		return movie_infoRepository.save(movie_info);
	}
	
	// 영화 수정
	public int movie_info_modify(Movie_info movie_info) {
		Movie_info info_old = movie_infoRepository.findById(movie_info.getMoviecode()).orElse(null);
		int result = 0;
		if (info_old != null) {
			// 2. 수정
			//movie_info.setReleasedate(info_old.getReleasedate());
			if (movie_info.getPoster1() == null || movie_info.getPoster1().equals("")) {
				movie_info.setPoster1(info_old.getPoster1());
			}
			if (movie_info.getTrailer() == null || movie_info.getTrailer().equals("")) {
				movie_info.setTrailer(info_old.getTrailer());
			}
			// 3. 저장
			Movie_info info_result = movie_infoRepository.save(movie_info);
			if (info_result != null) {
				result = 1;
			}
		}
		return result;
	}
	
	public int movie_info_delete(int moviecode) {
		// 1. 기존 데이터 가져오기
		Movie_info movie_info = movie_infoRepository.findById(moviecode).orElse(null);
		int result = 0;
		
		if (movie_info != null) {
			// 2. 삭제하기
			movie_infoRepository.delete(movie_info);
			// 3. 존재하는지 검사
			if (!movie_infoRepository.existsById(moviecode)) {
				result = 1;
			}
		}
		return result;
	}
}
