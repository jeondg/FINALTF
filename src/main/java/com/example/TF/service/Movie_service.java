package com.example.TF.service;

//import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TF.dao.Movie_MemberDAO;
import com.example.TF.dao.Movie_admin_board_DAO;
import com.example.TF.dao.Movie_infoDAO;
import com.example.TF.dao.Movie_info_stillcutDAO;
import com.example.TF.dao.Movie_question_boardDAO;
import com.example.TF.dao.Movie_reviewDAO;
import com.example.TF.dao.Movie_review_likeDAO;
import com.example.TF.dao.Movie_store_board_DAO;
import com.example.TF.dao.Movie_theater_DAO;
import com.example.TF.dao.Movie_theater_screen_DAO;
import com.example.TF.dao.Movie_user_qna_board_DAO;
import com.example.TF.dao.Movie_wishDAO;
import com.example.TF.dto.Movie_MemberDTO;
import com.example.TF.dto.Movie_question_boardDTO;
import com.example.TF.dto.Movie_reviewDTO;
import com.example.TF.dto.Movie_review_likeDTO;
import com.example.TF.dto.Movie_wishDTO;
import com.example.TF.entity.Movie_Member;
import com.example.TF.entity.Movie_admin_board;
import com.example.TF.entity.Movie_info;
import com.example.TF.entity.Movie_info_stillcut;
import com.example.TF.entity.Movie_question_board;
import com.example.TF.entity.Movie_review;
import com.example.TF.entity.Movie_store_board;
import com.example.TF.entity.Movie_theater;
import com.example.TF.entity.Movie_theater_screen;
import com.example.TF.entity.Movie_user_qna_board;
import com.example.TF.entity.Movie_wish;

@Service
public class Movie_service {
	
	// 공지 게시판
	@Autowired
	Movie_admin_board_DAO admin_dao;
	
	// 스토어
	@Autowired
	Movie_store_board_DAO store_dao;
	
	// 회원
	@Autowired
	Movie_MemberDAO member_dao;
	
	// 자주찾는질문
	@Autowired
	Movie_user_qna_board_DAO user_qna_dao;
	
	// 극장
	@Autowired
	Movie_theater_DAO theater_dao;
	
	// 상영관
	@Autowired
	Movie_theater_screen_DAO screen_dao;
	
	// 영화찜
	@Autowired
	Movie_wishDAO wish_dao;
	
	// 스틸컷
	@Autowired
	Movie_info_stillcutDAO stillcut_dao;
	
	// 리뷰등록
	@Autowired
	Movie_reviewDAO review_dao;
	
	// 영화정보
	@Autowired
	Movie_infoDAO info_dao;
	
	// 문의
	@Autowired
	Movie_question_boardDAO question_dao;
	
	// 답변좋아요
	@Autowired
	Movie_review_likeDAO like_dao;
	
	///////////////// 영화 스틸컷 ////////////////////
	// 사진 등록
	public Movie_info_stillcut stillcut_write(Movie_info_stillcut stillcut) {
		return stillcut_dao.stillcut_write(stillcut);
	}
	
	// 목록
	public List<Movie_info_stillcut> stillcut_list(int moviecode, int startNum, int endNum) {
		return stillcut_dao.stillcut_list(moviecode, startNum, endNum);
	}
	
	// 총 사진 수
	public int stillcut_get_count() {
		return stillcut_dao.get_count();
	}
	
	// 영화별 사진 수
	public int stillcut_get_count_moviecode(int moviecode) {
		return stillcut_dao.get_count_moviecode(moviecode);
	}
	
	// 상세보기
	public Movie_info_stillcut stillcut_view(int movie_stillcut_code) {
		return stillcut_dao.stillcut_view(movie_stillcut_code);
	}
	
	// 수정
	public int stillcut_modify(Movie_info_stillcut stillcut) {
		return stillcut_dao.stillcut_modify(stillcut);
	}
	
	// 삭제
	public int stillcut_delete(int movie_stillcut_code) {
		return stillcut_dao.stillcut_delete(movie_stillcut_code);
	}	

	
	// 공지
	
	// 목록 보기
	public List<Movie_admin_board> admin_board_list(int startNum, int endNum) {
		return admin_dao.admin_board_list(startNum, endNum);
	}
	
	// 총글수
	public int admin_get_count() {
		return admin_dao.get_count();
	}
	
	// 글쓰기
	public Movie_admin_board admin_board_write(Movie_admin_board board) {
		return admin_dao.admin_board_write(board);
	}
	
	// 상세보기 
	public Movie_admin_board admin_board_view(int seq) {
		return admin_dao.admin_board_view(seq);
	}
	
	// 글 수정
	public int admin_board_modify(Movie_admin_board board) {
		return admin_dao.admin_board_modify(board);
	}
	
	// 글 삭제
	public int admin_board_delete(int seq) {
		return admin_dao.admin_board_delete(seq);
	}
	
	// qna
	
	// 전체 목록 보기
	public List<Movie_user_qna_board> qna_list(int startNum, int endNum) {
		return user_qna_dao.qna_list(startNum, endNum);
	}
	
	// 총글수
	public int qna_get_count() {
		return user_qna_dao.get_count();
	}
	
	// 항목별 글수
	public int qna_get_count_section(String section) {
		return user_qna_dao.get_count_section(section);
	}
	
	// 개별 목록 보기
	public List<Movie_user_qna_board> qna_list_section(String section, int startNum, int endNum) {
		return user_qna_dao.qna_list_section(section, startNum, endNum);
	}
	
	// 글쓰기
	public Movie_user_qna_board qna_board_write(Movie_user_qna_board board) {
		return user_qna_dao.qna_board_write(board);
	}
	
	// 글 보기
	public Movie_user_qna_board qna_board_view(int seq) {
		return user_qna_dao.qna_board_view(seq);
	}
	
	// 글 수정
	public int qna_board_modify(Movie_user_qna_board board) {
		return user_qna_dao.qna_board_modify(board);
	}
	
	// 글 삭제
	public int qna_board_delete(int seq) {
		return user_qna_dao.qna_board_delete(seq);
	}
	
	// 스토어
	
	// 글쓰기
	public Movie_store_board store_board_write(Movie_store_board board) {
		return store_dao.store_board_write(board);
	}
	
	// 목록
	public List<Movie_store_board> store_board_list(int startNum, int endNum) {
		return store_dao.store_board_list(startNum, endNum);
	}
	
	// 총글수
	public int store_get_count() {
		return store_dao.get_count();
	}
	
	// 상세보기
	public Movie_store_board store_board_view(int seq) {
		return store_dao.store_board_view(seq);
	}
	
	// 수정
	public int store_board_modify(Movie_store_board board) {
		return store_dao.store_board_modify(board);
	}
	
	// 삭제
	public int store_board_delete(int seq) {
		return store_dao.store_board_delete(seq);
	}
	
	// 로그인 & 회원
	
	// 로그인
	public Movie_Member member_login(String id, String pwd) {
		return member_dao.login(id, pwd); 
	}
	
	// 회원정보 저장
	public Movie_Member member_write(Movie_MemberDTO dto) {
		return member_dao.write(dto);
	}
	
	// id 중복검사
	public boolean member_isExistId(String id) {
		return member_dao.isExistId(id);
	}
	
	// 1명의 회원정보읽기
	public Movie_Member member_getMember(String id) {
		return member_dao.getMember(id);
	}
	
	// 수정하기
	public boolean member_modify(Movie_MemberDTO dto) {
		return member_dao.modify(dto);
	}
	// 삭제하기
	public String member_delete(String id) {
		return member_dao.deleteMember(id);
	}
	// 문의내역 관리
	
	// 글 저장
	public Movie_question_board question_board_write(Movie_question_boardDTO dto) {
		return question_dao.question_board_write(dto);
	}
	
	// 상세보기
	public Movie_question_board question_board_view(int seq) {
		return question_dao.question_board_view(seq);
	}
	
	// 글 삭제
	public boolean question_board_delete(int seq) {
		return question_dao.question_board_delete(seq);
	}
	
	// 총 글수
	public int get_count() {
		return question_dao.get_count();
	}
	
	// 개인별 글수
	public int  get_count_qeustion_id(String question_id) {
		return question_dao.get_count_question_id(question_id);
	}
	
	// 전체 목록
	public List<Movie_question_board> question_list(int startNum, int endNum){
		return question_dao.question_list(startNum, endNum);
	}
	
	// 개별 목록
	public List<Movie_question_board> question_list_question_id(String question_id, int startNum, int endNum){
		return question_dao.question_list_question_id(question_id, startNum, endNum);
	}	
	
	////////// 자주찾는 질문 /////////
	
	// 전체 목록 보기
	public List<Movie_user_qna_board> user_qna_list(int startNum, int endNum) {
		return user_qna_dao.qna_list(startNum, endNum);
	}
	
	// 총글수
	public int user_qna_get_count() {
		return user_qna_dao.get_count();
	}
	
	// 항목별 글수
	public int user_qna_get_count_section(String section) {
		return user_qna_dao.get_count_section(section);
	}
	
	// 개별 목록 보기
	public List<Movie_user_qna_board> user_qna_list_section(String section, int startNum, int endNum) {
		return user_qna_dao.qna_list_section(section, startNum, endNum);
	}
	
	// 글 보기
	public Movie_user_qna_board user_qna_board_view(int seq) {
		return user_qna_dao.qna_board_view(seq);
	}
	
	// 극장 관리
	
	// 글쓰기
	public Movie_theater theater_write(Movie_theater theater) {
		return theater_dao.theater_write(theater);
	}
	
	// 목록
	public List<Movie_theater> theater_list(int startNum, int endNum) {
		return theater_dao.theater_list(startNum, endNum);
	}
	
	// 총글수
	public int theater_get_count() {
		return theater_dao.get_count();
	}
	
	// 상세보기
	public Movie_theater theater_view(int seq) {
		return theater_dao.theater_view(seq);
	}
	
	// 삭제
	public int theater_delete(int seq) {
		// 1. 기존 데이터 가져오기
		return theater_dao.theater_delete(seq);
	}
	
	// 수정
	public int theater_modify(Movie_theater theater) {
		return theater_dao.theater_modify(theater);
	}
	
	// 상영관 관리
	
	// 글쓰기
	public int screen_write(Movie_theater_screen screen) {
		return screen_dao.screen_write(screen);
	}
	
	// 목록

	public List<Movie_theater_screen> screen_list(
			int theater_code, int startNum, int endNum) {
		return screen_dao.screen_list(theater_code, startNum, endNum);
	}
	
	public List<Movie_theater_screen> screen_list_test(
			int startNum, int endNum) {
		return screen_dao.screen_list_test(startNum, endNum);
	}	
	
	// 총글수
	public int screen_get_count() {
		return screen_dao.get_count();
	}
	
	// 상세보기
	public Movie_theater_screen screen_view(int screen_num, int theater_code) {
		return screen_dao.screen_view(screen_num, theater_code);
	}
	
	// 수정
	public int screen_modify(Movie_theater_screen screen) {
		return screen_dao.screen_modify(screen);
	}
	
	// 삭제
	public int screen_delete(int screen_num, int theater_code) {
		return screen_dao.screen_delete(screen_num, theater_code);
	}	
	
	//////// 댓글 좋아요////////
	
	// 좋아요 추가
	public boolean addLike(Movie_review_likeDTO dto) {
	    if (!like_dao.existsLike(dto.getReviewcode(), dto.getUserId())) {
	    	like_dao.insertLike(dto);
	        return true;
	    }
	    return false; // 이미 좋아요 있음
	}

	// 좋아요 개수
	public int getLikeCount(int reviewcode) {
	    return like_dao.countLike(reviewcode);
	}

	// 좋아요 취소
	public void removeLike(int reviewcode, String user_id) {
		like_dao.removeLike(reviewcode, user_id);
	}

	//////// 영화 좋아요(찜) ////////
	
	// 유저가 해당 영화 찜했는지 확인
	public boolean isMovieWished(int moviecode, String user_id) {
	    return wish_dao.isWished(moviecode, user_id);
	}

	// 찜 추가
	public void addMovieWish(Movie_wishDTO dto) {
		wish_dao.addWish(dto);
	}

	// 찜 취소
	public void removeMovieWish(int moviecode, String user_id) {
		wish_dao.removeWish(moviecode, user_id);
	}

	// 찜 개수 조회
	public int getMovieWishCount(int moviecode) {
	    return wish_dao.getWishCount(moviecode);
	}
	
	public Set<Integer> getWishedMovieCodes(String userId) {
	    List<Movie_wish> wishList = wish_dao.findByUserId(userId);
	    return wishList.stream()
	            .map(Movie_wish::getMoviecode)
	            .collect(Collectors.toSet());
	}
	
	///////////////// 영화 리뷰 ////////////////////
	
	// 전체 목록 보기
	public List<Movie_review> review_list(int startNum, int endNum) {
		return review_dao.review_list(startNum, endNum);
	}
	
	// 총답글수
	public int review_count() {
		return review_dao.getreviewcount();
	}
	
	// 답글저장
	public Movie_review writereview(Movie_reviewDTO dto) {
		return review_dao.insertreview(dto);
	}
	
	public boolean checkReviewExists(int moviecode, String user_id) {
	    return review_dao.checkExists(moviecode, user_id);
	}
	
	public Movie_review getReviewByCode(int reviewcode) {
	    return review_dao.findByCode(reviewcode);
	}

	public void updateReview(Movie_review review) {
	    review_dao.save(review);  // JPA의 save는 수정도 됨 (PK 기준)
	}

	public void deleteReview(int reviewcode) {
	    review_dao.delete(reviewcode);
	}	
	
	// 최신순
	public List<Movie_reviewDTO> review_list_with_grade_paging(int moviecode, int start, int end) {
	    return review_dao.review_list_with_grade_paging(moviecode, start, end);
	}
	
	// 공감순
	public List<Movie_reviewDTO> review_list_by_like(int moviecode, int start, int end) {
	    return review_dao.review_list_by_like(moviecode, start, end);
	}
	
	// 리뷰수
	public int getReviewCount(int moviecode) {
	    return review_dao.countByMoviecode(moviecode);
	}	
	
	///////////////////영화정보////////////////////
	
	// 총영화수
	public int getCount() {
		return info_dao.get_count();
	}
	// 장르별 영화개수
	public int getgenreCount(String genre) {
		return info_dao.get_count_genre(genre);
	}
	// 국내영화수
	public int getcountryCount() {
		return info_dao.get_country();
	}
	// 해외영화수
	public int getforeignCount() {
		return info_dao.get_foreign();
	}
	
	// 영화번호순 목록
	public List<Movie_info> info_list_moviecode(int startNum, int endNum) {
		return info_dao.moviecode_list(startNum, endNum);
	}
	// 장르별목록
	public List<Movie_info> info_list_genre(String genre, int startNum, int endNum){
		return info_dao.genrerank_list(genre, startNum, endNum);
	}
	// 국내별목록
	public List<Movie_info> info_list_country(int startNum, int endNum){
		return info_dao.countryrank_list(startNum, endNum);
	}
	// 해외별목록
	public List<Movie_info> info_list_foreign(int startNum, int endNum){
		return info_dao.foreignrank_list(startNum, endNum);
	}
	// 박스오피스목록
	public List<Movie_info> info_list_boxoffice(int startNum, int endNum){
		return info_dao.boxoffice_list(startNum, endNum);
	}
	// 누적관객수목록
	public List<Movie_info> info_list_total(int startNum, int endNum){
		return info_dao.total_list(startNum, endNum);
	}
	// 개봉일순목록
	public List<Movie_info> info_list_releasedate(int startNum, int endNum){
		return info_dao.releasedate_list(startNum, endNum);
	}
	// 현재상영작목록
	public List<Movie_info> info_list_nowshowing(int startNum, int endNum){
		return info_dao.nowshowing_list(startNum, endNum);
	}
	// 상영예정작목록
	public List<Movie_info> willshow_country(int startNum, int endNum){
		return info_dao.willshow_list(startNum, endNum);
	}
	
	// 상세보기
	public Movie_info movie_info_view(int moviecode) {
		return info_dao.movie_info_view(moviecode);
	}
	
	// 영화 등록
	public Movie_info movie_info_write(Movie_info movie_info) {
		return info_dao.movie_info_write(movie_info);
	}	
	
	// 영화 수정
	public int movie_info_modify(Movie_info movie_info) {
		return info_dao.movie_info_modify(movie_info);
	}
	
	// 영화 삭제
	public int movie_info_delete(int moviecode) {
		return info_dao.movie_info_delete(moviecode);
	}	
	
}
