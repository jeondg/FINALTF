package com.example.TF.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.TF.dto.Movie_admin_board_DTO;
import com.example.TF.entity.Movie_admin_board;
import com.example.TF.service.Movie_service;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class Movie_admin_controller {

	@Autowired
	Movie_service service;
	
	// 관리자 메인 화면
	@GetMapping("/admin/admin_index")
	public String index(Model model) {
		model.addAttribute("req", "none");
		return "/admin/admin_index";
	}
	
	// 공지관리 게시판 목록 보기
	@GetMapping("/admin/admin_board_list")
	public String admin_board_list(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = 1;
		// 1) pg 저장
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		// 2) 목록 가져오기
		int endNum = pg * 5;
		int startNum = endNum - 4;
		
		List<Movie_admin_board> list = service.admin_board_list(startNum, endNum);
		
		// 3) 페이징 ㄷ이터
		int totalA = service.admin_get_count();
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
		model.addAttribute("req", "/admin/admin_board_list");
		return "/admin/admin_index";
	}
	
	// 글쓰기 폼
	@GetMapping("/admin/admin_board_writeForm")
	public String admin_board_writeForm(Model model) {
		model.addAttribute("req", "/admin/admin_board_writeForm");
		return "/admin/admin_index";
	}
	// 글쓰기 처리
	@PostMapping("/admin/admin_board_write")
	public String admin_board_write(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		String section = request.getParameter("section");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		Movie_admin_board_DTO dto = new Movie_admin_board_DTO();
		dto.setSection(section);
		dto.setSubject(subject);
		dto.setContent(content);
		dto.setLogtime(new Date());
		
		// entity 객체 생성
		Movie_admin_board board = dto.toEntity();
		// db 저장
		Movie_admin_board board_result = service.admin_board_write(board);
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
		model.addAttribute("req", "/admin/admin_board_write");
		return "/admin/admin_index";
	}
	
	// 글 상세보기
	@GetMapping("/admin/admin_board_view")
	public String admin_board_view(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pg = Integer.parseInt(request.getParameter("pg"));
		
		Movie_admin_board board = service.admin_board_view(seq);
		
		// 2. 데이터 공유
		model.addAttribute("board", board);
		model.addAttribute("pg", pg);
		model.addAttribute("seq", seq);
		
		// 3. view 파일명 리턴
		model.addAttribute("req", "/admin/admin_board_view");
		return "/admin/admin_index";
	}
	
	// 글 수정하기 폼
	@GetMapping("/admin/admin_board_modifyForm")
	public String admin_board_modifyForm(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pg = Integer.parseInt(request.getParameter("pg"));
		
		Movie_admin_board board = service.admin_board_view(seq);
		// 2. 데이터 공유
		model.addAttribute("pg", pg);
		model.addAttribute("seq", seq);
		model.addAttribute("board", board);
		model.addAttribute("req", "/admin/admin_board_modifyForm");
		return "/admin/admin_index";
	}
	
	// 글 수정 처리
	@PostMapping("/admin/admin_board_modify")
	public String admin_board_modify(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = Integer.parseInt(request.getParameter("pg"));
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		String section = request.getParameter("section");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		Movie_admin_board board = service.admin_board_view(seq);
		board.setSection(section);
		board.setSubject(subject);
		board.setContent(content);
		
		int result = service.admin_board_modify(board);
		
		// 2. 데이터 공유
		model.addAttribute("result", result);
		model.addAttribute("seq", seq);
		model.addAttribute("pg", pg);
		// 3. view 파일명 리턴
		model.addAttribute("req", "/admin/admin_board_modify");
		return "/admin/admin_index";
	}
	
	// 글 삭제
	@GetMapping("/admin/admin_board_delete")
	public String admin_board_delete(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = Integer.parseInt(request.getParameter("pg"));
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		// db 처리
		int result = service.admin_board_delete(seq);
		
		// 2. 데이터 공유
		model.addAttribute("result", result);
		model.addAttribute("pg", pg);
		model.addAttribute("seq", seq);
		// 3. view 파일명 리턴
		model.addAttribute("req", "/admin/admin_board_delete");
		return "/admin/admin_index";
	}
}
