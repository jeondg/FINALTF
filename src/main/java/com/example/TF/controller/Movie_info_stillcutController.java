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

import com.example.TF.dto.Movie_info_stillcutDTO;
import com.example.TF.entity.Movie_info;
import com.example.TF.entity.Movie_info_stillcut;
import com.example.TF.service.Movie_service;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class Movie_info_stillcutController {
	// 
	@Value("${project.upload.path}")
	private String uploadpath;
	
	@Autowired
	Movie_service service;
	
	@GetMapping("/stillcut/stillcut_writeForm")
	public String Movie_info_stillcut_writeForm(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = Integer.parseInt(request.getParameter("pg"));
		int moviecode = Integer.parseInt(request.getParameter("moviecode"));
		
		// 영화 정보
		Movie_info info = service.movie_info_view(moviecode);
		
		// 2. 데이터 공유
		model.addAttribute("pg", pg);
		model.addAttribute("moviecode", moviecode);
		model.addAttribute("info", info);
		return "/stillcut/stillcut_writeForm";
	}
	
	@PostMapping("/stillcut/stillcut_write")
	public String Movie_info_stillcut_write(Movie_info_stillcutDTO dto,
			Model model, @RequestParam("stillcut_img") MultipartFile uploadFile) {
		// 1. 데이터 처리
		// 파일명
		String fileName = uploadFile.getOriginalFilename();
		dto.setStillcut_src(fileName);
		
		// 파일 저장 폴더 만들기
		File folder = new File(uploadpath);
		if (!folder.exists()) {
			// 폴더가 없으면 만들기
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
		// entity 객체 생성
		//System.out.println(dto);
		Movie_info_stillcut stillcut = dto.toEntity();
		// db 저장
		Movie_info_stillcut stillcut_result = service.stillcut_write(stillcut);
		// 결과 확인
		String result = "";
		if (stillcut_result != null) {
			result = "사진 등록 성공";
		} else {
			result = "사진 등록 실패";
		}
		// 2. 데이터 공유
		model.addAttribute("result", result);
		model.addAttribute("stillcut_result", stillcut_result);
		model.addAttribute("fileName", fileName);
		// 3. view 파일명 리턴
		return "/stillcut/stillcut_write";
	}

	@GetMapping("/stillcut/stillcut_list")
	public String Movie_info_stillcut_list(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = 1;
		int moviecode = Integer.parseInt(request.getParameter("moviecode"));
		// 1) pg 저장
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		// 2) 목록 가져오기
		int endNum = pg * 6;
		int startNum = endNum - 5;
		
		List<Movie_info_stillcut> list = service.stillcut_list(moviecode, startNum, endNum);
		
		// 3) 페이징 데이터
		int totalA = service.stillcut_get_count_moviecode(moviecode);
		int totalP = (totalA + 5) / 6;
		
		int startPage = (pg-1)/3 * 3 + 1;
		int endPage = startPage + 2;
		if (endPage > totalP) endPage = totalP;
		
		// 2. 데이터 공유
		model.addAttribute("pg", pg);
		model.addAttribute("moviecode", moviecode);
		model.addAttribute("list", list);
		model.addAttribute("totalP", totalP);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		// 3. view 파일명 리턴		
		return "/stillcut/stillcut_list";
	}
	
	@GetMapping("/stillcut/stillcut_view")
	public String Movie_info_stillcut_view(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = Integer.parseInt(request.getParameter("pg"));
		int moviecode = Integer.parseInt(request.getParameter("moviecode"));
		int movie_stillcut_code = Integer.parseInt(request.getParameter("movie_stillcut_code"));
		
		Movie_info_stillcut stillcut = service.stillcut_view(movie_stillcut_code);
		Movie_info info = service.movie_info_view(moviecode);
		
		// 2. 데이터 공유
		model.addAttribute("pg", pg);
		model.addAttribute("moviecode", moviecode);
		model.addAttribute("stillcut", stillcut);
		model.addAttribute("info", info);
		return "/stillcut/stillcut_view";
	}
	
	@GetMapping("/stillcut/stillcut_delete")
	public String Movie_info_stillcut_delete(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = Integer.parseInt(request.getParameter("pg"));
		int moviecode = Integer.parseInt(request.getParameter("moviecode"));
		int movie_stillcut_code = Integer.parseInt(request.getParameter("movie_stillcut_code"));
		//System.out.println("movie_stillcut_code = " + movie_stillcut_code);
		int result = service.stillcut_delete(movie_stillcut_code);
		
		// 2. 데이터 공유
		model.addAttribute("result", result);
		model.addAttribute("pg", pg);
		model.addAttribute("moviecode", moviecode);
		// 3. view 파일명 리턴
		return "/stillcut/stillcut_delete";
	}
}
