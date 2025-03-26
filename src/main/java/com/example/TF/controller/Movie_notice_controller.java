package com.example.TF.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.TF.entity.Movie_admin_board;
import com.example.TF.service.Movie_service;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class Movie_notice_controller {

	@Autowired
	Movie_service service;

	
	// 공지관리 게시판 목록 보기
	@GetMapping("/customerServiceMain/notice_board_list")
	public String notice_board_list(Model model, HttpServletRequest request) {
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
		
		// 3) 페이징
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
		model.addAttribute("req2", "/customerServiceMain/notice_board_list");
		return "/main/customerServiceMain";
	}

	// 글 상세보기
	@GetMapping("/customerServiceMain/notice_board_view")
	public String notice_board_view(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pg = Integer.parseInt(request.getParameter("pg"));
		
		Movie_admin_board board = service.admin_board_view(seq);
		
		// 2. 데이터 공유
		model.addAttribute("board", board);
		model.addAttribute("pg", pg);
		model.addAttribute("seq", seq);
		
		// 3. view 파일명 리턴
		model.addAttribute("req2", "/customerServiceMain/notice_board_view");
		return "/main/customerServiceMain";
	}

}
