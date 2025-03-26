package com.example.TF.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.TF.dto.Movie_user_qna_board_DTO;
import com.example.TF.entity.Movie_user_qna_board;
import com.example.TF.service.Movie_service;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class Movie_qna_controller {
	
	@Autowired
	Movie_service service;
	
	// 메인 화면
	@GetMapping("/qna/qna_main")
	public String qna_main(Model model) {
		model.addAttribute("req", "none");
		return "/qna/qna_main";
	}
	
	// 전체 목록
	@GetMapping("/qna/qna_board_list_all")
	public String qna_list_all(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = 1;
		
		// 1) pg 저장
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		// 2) 목록 가져오기
		int endNum = pg * 5;
		int startNum = endNum - 4;
		
		List<Movie_user_qna_board> list = service.qna_list(startNum, endNum);
		
		// 3) 페이징 데이터
		int totalA = service.qna_get_count();
		int totalP = (totalA + 4) / 5;
		
		int startPage = (pg-1)/3 * 3 + 1;
		int endPage = startPage + 2;
		if (endPage > totalP) endPage = totalP;
		
		// 2. 데이터 공유
		model.addAttribute("pg", pg);
		model.addAttribute("list", list);
		model.addAttribute("totalP", totalP);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		// 3. view 파일명 리턴
		model.addAttribute("req", "/qna/qna_board_list_all");
		return "/qna/qna_main";
	}
	
	// 목록보기 - 홈페이지
	@GetMapping("/qna/qna_board_list_homepage")
	public String qna_list_homepage(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = 1;
		String homepage = "[홈페이지]";
		
		// 1) pg 저장
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		// 2) 목록 가져오기
		int endNum = pg * 5;
		int startNum = endNum - 4;
		
		List<Movie_user_qna_board> list = service.qna_list_section(homepage, startNum, endNum);
		
		// 3) 페이징 데이터
		int totalA = service.qna_get_count_section(homepage);
		int totalP = (totalA + 4) / 5;
		
		int startPage = (pg-1)/3 * 3 + 1;
		int endPage = startPage + 2;
		if (endPage > totalP) endPage = totalP;
		
		// 2. 데이터 공유
		model.addAttribute("pg", pg);
		model.addAttribute("list", list);
		model.addAttribute("totalP", totalP);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		// 3. view 파일명 리턴
		model.addAttribute("req", "/qna/qna_board_list_homepage");
		return "/qna/qna_main";
	}
	
	// 목록보기 - 영화관이용
	@GetMapping("/qna/qna_board_list_theater")
	public String qna_list_theater(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = 1;
		String theater = "[영화관이용]";
		
		// 1) pg 저장
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		// 2) 목록 가져오기
		int endNum = pg * 5;
		int startNum = endNum - 4;
		
		List<Movie_user_qna_board> list = service.qna_list_section(theater, startNum, endNum);
		
		// 3) 페이징 데이터
		int totalA = service.qna_get_count_section(theater);
		int totalP = (totalA + 4) / 5;
		
		int startPage = (pg-1)/3 * 3 + 1;
		int endPage = startPage + 2;
		if (endPage > totalP) endPage = totalP;
		
		// 2. 데이터 공유
		model.addAttribute("pg", pg);
		model.addAttribute("list", list);
		model.addAttribute("totalP", totalP);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		// 3. view 파일명 리턴
		model.addAttribute("req", "/qna/qna_board_list_theater");
		return "/qna/qna_main";
	}
	
	// 목록보기 - 예매관람권
	@GetMapping("/qna/qna_board_list_reserve")
	public String qna_list_reserve(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = 1;
		String reserve = "[예매관람권]";

		// 1) pg 저장
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		// 2) 목록 가져오기
		int endNum = pg * 5;
		int startNum = endNum - 4;

		List<Movie_user_qna_board> list = service.qna_list_section(reserve, startNum, endNum);

		// 3) 페이징 데이터
		int totalA = service.qna_get_count_section(reserve);
		int totalP = (totalA + 4) / 5;

		int startPage = (pg - 1) / 3 * 3 + 1;
		int endPage = startPage + 2;
		if (endPage > totalP)
			endPage = totalP;

		// 2. 데이터 공유
		model.addAttribute("pg", pg);
		model.addAttribute("list", list);
		model.addAttribute("totalP", totalP);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		// 3. view 파일명 리턴
		model.addAttribute("req", "/qna/qna_board_list_reserve");
		return "/qna/qna_main";
	}
	
	// 글 쓰기 폼
	@GetMapping("/qna/qna_board_writeForm")
	public String qna_board_writeForm(Model model) {
		model.addAttribute("req", "/qna/qna_board_writeForm");
		return "/qna/qna_main";
	}
	
	// 글 쓰기 처리
	@PostMapping("/qna/qna_board_write")
	public String qna_board_write(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		String section = request.getParameter("section");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		Movie_user_qna_board_DTO dto = new Movie_user_qna_board_DTO();
		dto.setSection(section);
		dto.setSubject(subject);
		dto.setContent(content);
		dto.setLogtime(new Date());
		
		// entity 객체 생성
		Movie_user_qna_board board = dto.toEntity();
		// db 저장
		Movie_user_qna_board board_result = service.qna_board_write(board);
		// 결과 확인
		String result = "";
		if (board_result != null) {
			result = "저장 성공";
		} else {
			result = "저장 실패";
		}
		// 2. 데이터 공유
		model.addAttribute("result", result);
		model.addAttribute("board", board);
		// 3. view 파일명 리턴
		model.addAttribute("req", "/qna/qna_board_write");
		return "/qna/qna_main";
	}	
	
	// 글 보기
	@GetMapping("/qna/qna_board_view")
	public String qna_board_view(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pg = Integer.parseInt(request.getParameter("pg"));
		
		Movie_user_qna_board board = service.qna_board_view(seq);
		
		// 2. 데이터 공유
		model.addAttribute("board", board);
		model.addAttribute("pg", pg);
		model.addAttribute("seq", seq);
		
		// 3. view 파일명 리턴
		model.addAttribute("req", "/qna/qna_board_view");
		return "/qna/qna_main";
	}
	
	// 글 수정하기 폼
	@GetMapping("/qna/qna_board_modifyForm")
	public String qna_board_modifyForm(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pg = Integer.parseInt(request.getParameter("pg"));
		
		Movie_user_qna_board board = service.qna_board_view(seq);
		// 2. 데이터 공유
		model.addAttribute("pg", pg);
		model.addAttribute("seq", seq);
		model.addAttribute("board", board);
		model.addAttribute("req", "/qna/qna_board_modifyForm");
		return "/qna/qna_main";
	}
	
	// 글 수정 처리
	@PostMapping("/qna/qna_board_modify")
	public String qna_board_modify(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = Integer.parseInt(request.getParameter("pg"));
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		String section = request.getParameter("section");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		Movie_user_qna_board board = service.qna_board_view(seq);
		board.setSection(section);
		board.setSubject(subject);
		board.setContent(content);
		
		int result = service.qna_board_modify(board);
		
		// 2. 데이터 공유
		model.addAttribute("result", result);
		model.addAttribute("seq", seq);
		model.addAttribute("pg", pg);
		// 3. view 파일명 리턴
		model.addAttribute("req", "/qna/qna_board_modify");
		return "/qna/qna_main";
	}
	
	// 글 삭제
	@GetMapping("/qna/qna_board_delete")
	public String qna_board_delete(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = Integer.parseInt(request.getParameter("pg"));
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		// db 처리
		int result = service.qna_board_delete(seq);
		
		// 2. 데이터 공유
		model.addAttribute("result", result);
		model.addAttribute("pg", pg);
		model.addAttribute("seq", seq);
		
		// 3. view 파일명 리턴
		model.addAttribute("req", "/qna/qna_board_delete");
		return "/qna/qna_main";
	}
}
