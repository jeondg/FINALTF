package com.example.TF.controller;

import java.io.File;
import java.io.IOException;
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

import com.example.TF.dto.Movie_question_boardDTO;
import com.example.TF.entity.Movie_question_board;
import com.example.TF.service.Movie_service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class Movie_question_boardController {
	@Autowired
	Movie_service service;
	
	// "D:/JDG/spring_boot/workspace/static/movie"
	@Value("${project.upload.movie.path}")
	private String uploadpath;
	
	// 1:1문의하기
	@GetMapping("/customerServiceMain/question_board_writeForm")
	public String question_board_writeForm(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		model.addAttribute("memId", session.getAttribute("memId"));
		model.addAttribute("memName", session.getAttribute("memName"));
		
		model.addAttribute("req2", "/customerServiceMain/question_board_writeForm");
		return "/main/customerServiceMain";
	}
	
	@PostMapping("/customerServiceMain/question_board_write")
	public String question_board_write(Movie_question_boardDTO dto, Model model,
			@RequestParam("img") MultipartFile uploadFile,
			HttpServletRequest request){
		// 데이터 처리
		
		// 파일명 저장
		String fileName = uploadFile.getOriginalFilename();
		

		dto.setAnswer_content(null);
		dto.setQuestion_stat("미답변");
		dto.setImage1(fileName);
		dto.setLogtime(new Date()); // 등록일
		
		// 파일 저장 폴더 만들기
		File folder = new File(uploadpath);
		if(!folder.exists()) {
			folder.mkdirs();
		}
		
		// 전달된 파일 있으면 저장
		if(!fileName.equals("")) {
			File file = new File(uploadpath, fileName);
			try {
				uploadFile.transferTo(file);	// 파일 저장하기
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		// db
		Movie_question_board movie_question_board = service.question_board_write(dto);
		
		String result = "";
		if(movie_question_board != null) {
			result = "저장 성공";
		} else {
			result = "저장 실패";
		}
		
		// 데이터 공유
		model.addAttribute("result", result);
		model.addAttribute("movie_question_board",movie_question_board);
		// view 파일명 리턴
		model.addAttribute("req2", "/customerServiceMain/question_board_write");
		return "/main/customerServiceMain";
	}
	@GetMapping("/mypage/question_board_user_list")
	public String question_list_question_id(Model model, HttpServletRequest request){
		// 데이터 처리
		int pg = 1;
		HttpSession session = request.getSession();
		String question_id = (String)session.getAttribute("memId");

		if(request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}

		int endNum = pg * 5;
		int startNum = endNum - 4;
		
		List<Movie_question_board>list = service.question_list_question_id(question_id, startNum, endNum);
		System.out.println("list" + list);
		// 페이징
		int totalA = service.get_count_qeustion_id(question_id);
		int totalP = Math.max((totalA +4) / 5, 1);
		
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
		model.addAttribute("req", "/mypage/question_board_user_list");
		return "/main/mypageMain";		
	}
	@GetMapping("/mypage/question_board_view")
	public String question_board_view(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pg = Integer.parseInt(request.getParameter("pg"));	
		
		Movie_question_board board = service.question_board_view(seq);
		
		// 2. 데이터 공유
		model.addAttribute("board", board);
		model.addAttribute("pg", pg);
		model.addAttribute("seq", seq);	
		
		// 3. view 파일명 리턴
		model.addAttribute("req", "/mypage/question_board_view");
		return "/main/mypageMain";
	}
	
	@GetMapping("/mypage/question_board_delete")
	public String question_board_delte(Model model, HttpServletRequest request) {
		// 데이터 처리
		int pg = Integer.parseInt(request.getParameter("pg"));
		int seq = Integer.parseInt(request.getParameter("seq"));
		String result = "";
		// db 처리
		boolean boolean_result = service.question_board_delete(seq);
		if(boolean_result) {
			result="삭제 성공";
		} else {
			result = "삭제 실패";
		}
		// 2. 데이터 공유
		model.addAttribute("result", result);
		model.addAttribute("pg", pg);
		model.addAttribute("seq", seq);
		
		// 3. view 파일명 리턴
		model.addAttribute("req", "/mypage/question_board_delete");
		return "/main/mypageMain";
		
	}
}
