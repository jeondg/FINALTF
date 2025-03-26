package com.example.TF.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.TF.dto.Movie_infoDTO;
import com.example.TF.entity.Movie_info;
import com.example.TF.service.Movie_service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class Movie_infoController {
	@Autowired
	Movie_service service;
	
	// "D:/JDG/spring_boot/workspace/static/movie_poster"
	@Value("${project.upload.movie_poster.path}")
	private String uploadpath;
	
	// 영화정보보기
	@GetMapping("/movieinfo/moviedetail")
	public String moviedetail(@RequestParam(name = "moviecode", defaultValue = "0") int moviecode,
			Movie_infoDTO dto, Model model, HttpSession session) {
		
		// 영화 상세정보
		Movie_info movie_info = service.movie_info_view(dto.getMoviecode());
		
		// 리뷰수 가져오기
		int reviewCount = service.getReviewCount(moviecode);
		
		// 로그인한 id
		String memid = (String) session.getAttribute("memId");
		
		model.addAttribute("sessionMemid", memid);
		model.addAttribute("moviecode", moviecode);
		model.addAttribute("movie_info", movie_info);
		model.addAttribute("reviewCount", reviewCount);
		
		return "/movieinfo/moviedetail";
	}
	
	// 영화 리스트
	@GetMapping("/movieinfo/movie_info_list")
	public String movie_info_list(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = 1;
		// 1) pg 저장
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		// 2) 목록 가져오기
		int endNum = pg * 10;
		int startNum = endNum - 9;
		
		List<Movie_info> list = service.info_list_moviecode(startNum, endNum);
		
		// 3) 페이징 데이터
		int totalA = service.getCount();
		int totalP = (totalA + 9) / 10;
		
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
		return "/movieinfo/movie_info_list";
	}
	
	@GetMapping("/movieinfo/movie_info_writeForm")
	public String movie_info_writeForm() {
		return "/movieinfo/movie_info_writeForm";
	}
	
	@PostMapping("/movieinfo/movie_info_write")
	public String movie_info_write(Model model, HttpServletRequest request,
			@RequestParam("movie_img") MultipartFile uploadFile) {
		// 1. 데이터 처리
		String title = request.getParameter("title");
		String synopsis = request.getParameter("synopsis");
		String country = request.getParameter("country");
		String director = request.getParameter("director");
		int runningtime = Integer.parseInt(request.getParameter("runningtime"));
		int agerating = Integer.parseInt(request.getParameter("agerating"));
		// releasedate 데이터 변환 필요 
		String releasedate = request.getParameter("releasedate");
		Date releasedate_str = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			releasedate_str = sdf.parse(releasedate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//
		String genre = request.getParameter("genre");
		// 배우진 ??
		String actors = request.getParameter("actors");
		String trailer = request.getParameter("trailer");
		
		// 파일명
		String fileName = uploadFile.getOriginalFilename();
		System.out.println("fileName = " + fileName);
		// yesterday, total?
		
		Movie_infoDTO dto = new Movie_infoDTO();
		dto.setTitle(title);
		dto.setSynopsis(synopsis);
		dto.setCountry(country);
		dto.setDirector(director);
		dto.setRunningtime(runningtime);
		dto.setAgerating(agerating);
		dto.setReleasedate(releasedate_str);
		dto.setGenre(genre);
		dto.setActors(actors);
		dto.setPoster1(fileName);
		dto.setYesterday(0);
		dto.setTotal(0);
		dto.setTrailer(trailer);
		
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
		Movie_info movie_info = dto.toEntity();
		// db 저장
		Movie_info info_result = service.movie_info_write(movie_info);
		// 결과 확인
		String result = "";
		if (info_result != null) {
			result = "영화 등록 성공";
		} else {
			result = "영화 등록 실패";
		}
		
		// 2. 데이터 공유
		model.addAttribute("result", result);
		model.addAttribute("info_result", info_result);
		model.addAttribute("fileName", fileName);
		// 3. view 파일명 리턴
		return "/movieinfo/movie_info_write";
	}	
	
	@GetMapping("/movieinfo/movie_info_view")
	public String movie_info_view(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = Integer.parseInt(request.getParameter("pg"));
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		Movie_info info = service.movie_info_view(seq);
		
		// 2. 데이터 공유
		model.addAttribute("pg", pg);
		model.addAttribute("seq", seq);
		model.addAttribute("info", info);
		return "/movieinfo/movie_info_view";
	}
	
	@GetMapping("/movieinfo/movie_info_modifyForm")
	public String movie_info_modifyForm(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = Integer.parseInt(request.getParameter("pg"));
		int seq = Integer.parseInt(request.getParameter("seq"));		
		
		Movie_info info = service.movie_info_view(seq);
		
		// 2. 데이터 공유
		model.addAttribute("pg", pg);
		model.addAttribute("seq", seq);
		model.addAttribute("info", info);
		// 3. view 파일명 리턴
		return "/movieinfo/movie_info_modifyForm";
	}
	
	@PostMapping("/movieinfo/movie_info_modify")
	public String movie_info_modify(Model model, 
			HttpServletRequest request,
			@RequestParam("movie_img") MultipartFile uploadFile) {
		// 1. 데이터 처리
		int pg = Integer.parseInt(request.getParameter("pg"));
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		String title = request.getParameter("title");
		String synopsis = request.getParameter("synopsis");
		String country = request.getParameter("country");
		String director = request.getParameter("director");
		int runningtime = Integer.parseInt(request.getParameter("runningtime"));
		int agerating = Integer.parseInt(request.getParameter("agerating"));
		// releasedate 데이터 변환 필요 
		String releasedate = request.getParameter("releasedate");
		Date releasedate_str = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			releasedate_str = sdf.parse(releasedate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//
		String genre = request.getParameter("genre");
		// 배우진 ??
		String actors = request.getParameter("actors");
		String trailer = request.getParameter("trailer");
		
		// 파일명
		String fileName = uploadFile.getOriginalFilename();
		System.out.println("fileName = " + fileName);
		
		Movie_infoDTO dto = new Movie_infoDTO();
		dto.setMoviecode(seq);
		dto.setTitle(title);
		dto.setSynopsis(synopsis);
		dto.setCountry(country);
		dto.setDirector(director);
		dto.setRunningtime(runningtime);
		dto.setAgerating(agerating);
		dto.setReleasedate(releasedate_str);
		dto.setGenre(genre);
		dto.setActors(actors);
		dto.setPoster1(fileName);
		dto.setYesterday(0);
		dto.setTotal(0);
		dto.setTrailer(trailer);
		
		System.out.println(dto);
		// entity 객체 생성
		Movie_info info = dto.toEntity();
		
		int result = service.movie_info_modify(info);
		
		// 2. 데이터 공유
		model.addAttribute("result", result);
		model.addAttribute("seq", seq);
		model.addAttribute("pg", pg);
		return "/movieinfo/movie_info_modify";
	}
	
	@GetMapping("/movieinfo/movie_info_delete")
	public String movie_info_delete(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = Integer.parseInt(request.getParameter("pg"));
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		int result = service.movie_info_delete(seq);
		
		// 2. 데이터 공유
		model.addAttribute("result", result);
		model.addAttribute("pg", pg);
		model.addAttribute("seq", seq);
		// 3. view 파일명 리턴
		return "/movieinfo/movie_info_delete";
	}	
}
