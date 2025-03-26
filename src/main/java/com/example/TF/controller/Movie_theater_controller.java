package com.example.TF.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.TF.dto.Movie_theater_DTO;
import com.example.TF.entity.Movie_theater;
import com.example.TF.entity.Movie_theater_screen;
import com.example.TF.service.Movie_service;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class Movie_theater_controller {
	
	@Value("${project.upload.path}")
	private String uploadpath;
	
	@Autowired
	Movie_service service;
	
	@GetMapping("/theater/theater_writeForm")
	public String theater_writeForm() {
		return "/theater/theater_writeForm";
	}
	
	@PostMapping("/theater/theater_write")
	public String theater_write(Movie_theater_DTO dto,
			Model model, @RequestParam("theater_img") MultipartFile uploadFile) {
		
		// 1. 데이터 처리
		// 파일명
		String fileName = uploadFile.getOriginalFilename();
		dto.setTheater_image1(fileName);
		System.out.println("fileName = " + fileName);
		
		// 파일 저장 폴더 만들기
		File folder = new File(uploadpath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		// 전달된 파일이 있으면 저장하기
		if (!fileName.equals("")) {
			File file = new File(uploadpath, fileName);
			try {
				uploadFile.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(dto);
		// entity 객체 생성
		Movie_theater theater = dto.toEntity();
		// db 저장
		Movie_theater theater_result = service.theater_write(theater);
		// 결과 확인
		String result = "";
		if (theater_result != null) {
			result = "극장 등록 완료";
		} else {
			result = "극장 등록 실패";
		}
		// 2. 데이터 공유
		model.addAttribute("result", result);
		model.addAttribute("theater_result", theater_result);
		model.addAttribute("fileName", fileName);
		// 3. view 파일명 리턴
		return "/theater/theater_write";
	}
	
	@GetMapping("/theater/theater_list")
	public String theater_list(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = 1;
		// 1) pg 저장
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		// 2) 목록 가져오기
		int endNum = pg * 5;
		int startNum = endNum - 4;
		
		List<Movie_theater> list = service.theater_list(startNum, endNum);
		
		// 3) 페이징 데이터
		int totalA = service.theater_get_count();
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
		return "/theater/theater_list";
	}
	
	@GetMapping(value = "/theater/theater_view")
	public String theater_view(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = Integer.parseInt(request.getParameter("pg"));
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		Movie_theater theater = service.theater_view(seq);
		/// seq => 극장 번호
		// 2. 데이터 공유
		model.addAttribute("pg", pg);
		model.addAttribute("seq", seq);
		model.addAttribute("theater", theater);
		//model.addAttribute("req", "/theater/screen/screen_list?seq=" + seq);
		
		// 상영관 목록 정보
		int screen_pg = 1;
		// 1) pg 저장
		if (request.getParameter("screen_pg") != null) {
			screen_pg = Integer.parseInt(request.getParameter("screen_pg"));
		}
		
		// 2) 목록 가져오기
		int endNum = screen_pg * 5;
		int startNum = endNum - 4;
		
		List<Movie_theater_screen> list = service.screen_list(seq, startNum, endNum);
		//List<Movie_theater_screen> list = service.screen_list_test(startNum, endNum);
		System.out.println("seq = " + seq);
		// 3) 페이징 데이터
		int totalA = service.screen_get_count();
		int totalP = (totalA + 4) / 5;
		
		int startPage = (screen_pg-1)/3 * 3 + 1;
		int endPage = startPage + 2;
		if (endPage > totalP) endPage = totalP;
		
		// 2. 데이터 공유
		model.addAttribute("screen_pg", screen_pg);
		model.addAttribute("list", list);
		model.addAttribute("totalP", totalP);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);	
		
		model.addAttribute("req", "/theater/screen/screen_list");
		return "/theater/theater_view";
	}

	@GetMapping(value = "/theater/theater_delete")
	public String theater_delete(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = Integer.parseInt(request.getParameter("pg"));
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		int result = service.theater_delete(seq);
		
		// 2. 데이터 공유
		model.addAttribute("result", result);
		model.addAttribute("pg", pg);
		model.addAttribute("seq", seq);
		// 3. view 파일명 리턴
		return "/theater/theater_delete";
	}
	
	// 극장 수정
	@GetMapping("/theater/theater_modifyForm")
	public String theater_modfiyForm(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = Integer.parseInt(request.getParameter("pg"));
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		Movie_theater theater = service.theater_view(seq);
		
		// 2. 데이터 공유
		model.addAttribute("pg", pg);
		model.addAttribute("seq", seq);
		model.addAttribute("theater", theater);
		// 3. view 파일명 리턴
		//model.addAttribute("req", "/store/store_board_modifyForm");
		return "/theater/theater_modifyForm";
	}	
	
	// 극장 수정 처리
	@PostMapping("/theater/theater_modify")
	public String theater_modify(Movie_theater_DTO dto, Model model, HttpServletRequest request,
			@RequestParam("theater_img") MultipartFile uploadFile) {
		// 1. 데이터 처리
		int pg = Integer.parseInt(request.getParameter("pg"));
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		String fileName = uploadFile.getOriginalFilename();
		dto.setTheater_image1(fileName);
		
		// entity 객체 생성
		Movie_theater theater = dto.toEntity();
		
		int result = service.theater_modify(theater);
		
		// 2. 데이터 공유
		model.addAttribute("result", result);
		model.addAttribute("seq", seq);
		model.addAttribute("pg", pg);
		// 3. view 파일명 리턴
		//model.addAttribute("req", "/store/store_board_modify");
		return "/theater/theater_modify";
	}	
}
