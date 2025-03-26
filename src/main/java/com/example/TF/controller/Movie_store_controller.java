package com.example.TF.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.TF.dto.Movie_store_board_DTO;
import com.example.TF.entity.Movie_store_board;
import com.example.TF.service.Movie_service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class Movie_store_controller {
	
	@Value("${project.upload.path}")
	private String uploadpath;
	
	@Autowired
	Movie_service service;
	
	// 스토어 글쓰기
	@GetMapping("/store/store_board_writeForm")
	public String store_board_writeForm(Model model) {
		model.addAttribute("req", "/store/store_board_writeForm");
		return "/admin/admin_index";
	}

	// 스토어 글쓰기 처리
	@PostMapping("/store/store_board_write")
	public String store_board_write(Movie_store_board_DTO dto, Model model,
			@RequestParam("img") MultipartFile uploadFile) {
		System.out.println(dto);
		// 1. 데이터 처리
		// 파일명
		String fileName = uploadFile.getOriginalFilename();
		dto.setImage1(fileName);
		dto.setLogtime(new Date());
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
		Movie_store_board board = dto.toEntity();
		// db 저장
		Movie_store_board board_result = service.store_board_write(board);
		// 결과 확인
		String result = "";
		if (board_result != null) {
			result = "상품을 저장하였습니다.";
		} else {
			result = "상품 저장에 실패하였습니다.";
		}
		// 2. 데이터 공유
		model.addAttribute("result", result);
		model.addAttribute("board_result", board_result);
		model.addAttribute("fileName", fileName);
		// 3. view 파일명 리턴
		model.addAttribute("req", "/store/store_board_write");
		return "/admin/admin_index";
	}

	// 스토어 리스트
	@GetMapping("/store/store_board_list")
	public String store_board_list(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = 1;
		// 1) pg 저장
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		// 2) 목록 가져오기
		int endNum = pg * 5;
		int startNum = endNum - 4;
		
		List<Movie_store_board> list = service.store_board_list(startNum, endNum);
		
		// 3) 페이징 데이터
		int totalA = service.store_get_count();
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
		model.addAttribute("req", "/store/store_board_list");
		return "/admin/admin_index";
	}

	// 스토어 글보기
	@GetMapping("/store/store_board_view")
	public String store_board_view(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = Integer.parseInt(request.getParameter("pg"));
		int seq = Integer.parseInt(request.getParameter("seq"));

		Movie_store_board board = service.store_board_view(seq);

		// 2. 데이터 공유
		model.addAttribute("pg", pg);
		model.addAttribute("seq", seq);
		model.addAttribute("board", board);
		model.addAttribute("req", "/store/store_board_view");
		return "/admin/admin_index";
	}

	// 스토어 글 삭제
	@GetMapping("/store/store_board_delete")
	public String store_board_delete(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = Integer.parseInt(request.getParameter("pg"));
		int seq = Integer.parseInt(request.getParameter("seq"));

		int result = service.store_board_delete(seq);

		// 2. 데이터 공유
		model.addAttribute("pg", pg);
		model.addAttribute("seq", seq);
		model.addAttribute("result", result);
		// 3. view 파일명 리턴
		model.addAttribute("req", "/store/store_board_delete");
		return "/admin/admin_index";
	}

	// 스토어 글 수정
	@GetMapping("/store/store_board_modifyForm")
	public String store_board_modifyForm(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = Integer.parseInt(request.getParameter("pg"));
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		Movie_store_board board = service.store_board_view(seq);
		
		// 2. 데이터 공유
		model.addAttribute("pg", pg);
		model.addAttribute("seq", seq);
		model.addAttribute("board", board);
		// 3. view 파일명 리턴
		model.addAttribute("req", "/store/store_board_modifyForm");
		return "/admin/admin_index";
	}

	// 스토어 글 수정 처리
	@PostMapping("/store/store_board_modify")
	public String store_board_modify(Movie_store_board_DTO dto, Model model, HttpServletRequest request,
			@RequestParam("img") MultipartFile uploadFile) {
		// 1. 데이터 처리

		int pg = Integer.parseInt(request.getParameter("pg"));
		int seq = Integer.parseInt(request.getParameter("seq"));

		String fileName = uploadFile.getOriginalFilename();
		dto.setImage1(fileName);
		dto.setLogtime(new Date());

		// entity 객체 생성
		Movie_store_board board = dto.toEntity();

		int result = service.store_board_modify(board);

		// 2. 데이터 공유
		model.addAttribute("result", result);
		model.addAttribute("seq", seq);
		model.addAttribute("pg", pg);
		// 3. view 파일명 리턴
		model.addAttribute("req", "/store/store_board_modify");
		return "/admin/admin_index";
	}

	// 스토어 리스트2 (수민)
	@GetMapping("/store/store_board_list2")
	public String store_board_list2(Model model, HttpServletRequest request) {
	    int pg = 1;
	    if (request.getParameter("pg") != null) {
	        pg = Integer.parseInt(request.getParameter("pg"));
	    }

	    // 8개씩 보여지도록 설정 변경
	    int endNum = pg * 8;
	    int startNum = endNum - 7;

	    List<Movie_store_board> list = service.store_board_list(startNum, endNum);

	    int totalA = service.store_get_count();
	    int totalP = (totalA + 7) / 8;

	    int startPage = (pg - 1) / 3 * 3 + 1;
	    int endPage = startPage + 2;
	    if (endPage > totalP) endPage = totalP;

	    model.addAttribute("pg", pg);
	    model.addAttribute("list", list);
	    model.addAttribute("totalP", totalP);
	    model.addAttribute("startPage", startPage);
	    model.addAttribute("endPage", endPage);

	    model.addAttribute("req", "/store/store_board_list2");
		return "/store/store_board_list2";
	}
	
	// 스토어 글보기2 (수민)
	@GetMapping("/store/store_board_view2")
	public String store_board_view2(Model model, HttpServletRequest request) {
	    int pg = Integer.parseInt(request.getParameter("pg"));
	    int seq = Integer.parseInt(request.getParameter("seq"));

	    Movie_store_board board = service.store_board_view(seq);

	    model.addAttribute("pg", pg);
	    model.addAttribute("seq", seq);
	    model.addAttribute("board", board);
	    model.addAttribute("req", "/store/store_board_view2");
	    return "/store/store_board_view2";
	}
	
	
	
	// 선택한 상품 결제 페이지로 이동 (수민)
	@PostMapping("/store/store_payment")
	public String store_payment(@RequestParam("seqList") List<Integer> seqList, Model model) {

	    List<Movie_store_board> selectedProducts = seqList.stream()
	        .map(seq -> service.store_board_view(seq))
	        .collect(Collectors.toList());

	    int totalPrice = selectedProducts.stream()
	        .mapToInt(Movie_store_board::getImageprice)
	        .sum();

	    model.addAttribute("selectedProducts", selectedProducts);
	    model.addAttribute("totalPrice", totalPrice);
	    model.addAttribute("req", "/store/store_payment");

	    return "/admin/admin_index";
	}

}
