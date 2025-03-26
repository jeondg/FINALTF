package com.example.TF.controller;

import java.io.File;
import java.io.IOException;
//import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.TF.dto.Movie_theater_screen_DTO;
import com.example.TF.entity.Movie_theater_screen;
import com.example.TF.entity.Screen_seat;
import com.example.TF.service.Movie_service;
import com.example.TF.service.Seat_service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class Movie_theater_screen_controller {

	@Value("${project.upload.path}")
	private String uploadpath;
	
	@Autowired
	Movie_service service;
	
	@Autowired
	Seat_service seat_service;
	
	// 상영관 추가 폼
	@GetMapping("/theater/screen/screen_writeForm")
	public String screen_writeForm(Model model, HttpServletRequest request) {

		int seq = Integer.parseInt(request.getParameter("seq"));
		int pg = Integer.parseInt(request.getParameter("pg"));

		// 통로 정보
		String aisle = request.getParameter("aisle");
		if (aisle != null && !aisle.isEmpty()) {
			String[] aisleValues = aisle.split(",");
			int[] aisle_values = new int[aisleValues.length];
			for (int i=0; i<aisleValues.length; i++) {	
				aisle_values[i] = Integer.parseInt(aisleValues[i]);		
			}
			model.addAttribute("aisle_values", aisle_values);
		}
		
		// 열 정보용 문자
		String[] row_array = {"A", "B", "C", "D", "E", 
							 "F", "G", "H", "I", "J", 
							 "K", "L", "M", "N", "O", 
							 "P", "Q", "R", "S", "T", 
							 "U", "V", "W", "X", "Y", 
							 "Z"};
		
		// 상영관 번호
		int screen_num = 0;
		if (request.getParameter("screen_num") != null) {
			screen_num = Integer.parseInt(request.getParameter("screen_num"));
		}
		
		// 상영관명
		String screen_name = "";
		if (request.getParameter("screen_name") != null) {
			screen_name = request.getParameter("screen_name");
		}
		// 상영관 기본 가격
		String screen_cost = "";
		if (request.getParameter("screen_cost") != null) {
			screen_cost = request.getParameter("screen_cost");
		}
		// 열당 좌석 수 
		int x_index = 0;
		if (request.getParameter("x_index") != null) {
			x_index = Integer.parseInt(request.getParameter("x_index"));
		}
		// 열의 수
		int y_index = 0;
		if (request.getParameter("y_index") != null) {
			y_index = Integer.parseInt(request.getParameter("y_index"));
		}
		
		int x_index_seat = x_index*2 - 1;
		
		// 2. 데이터 공유
		
		model.addAttribute("screen_num", screen_num);
		model.addAttribute("screen_name", screen_name);
		model.addAttribute("screen_cost", screen_cost);
		model.addAttribute("x_index", x_index);
		model.addAttribute("x_index_seat", x_index_seat);
		model.addAttribute("y_index", y_index);
		model.addAttribute("aisle", aisle);
		model.addAttribute("row_array", row_array);

		// 극장 번호
		model.addAttribute("seq", seq);
		model.addAttribute("pg", pg);
		return "/theater/screen/screen_writeForm";
	}

	// 상영관 추가 처리
	@PostMapping("/theater/screen/screen_write")
	public String screen_write(Movie_theater_screen_DTO dto,
			Model model, 
			@RequestParam("screen_img") MultipartFile uploadFile,
			HttpServletRequest request,
			@RequestParam("seats_info") String seats_info) {
		
		// 상영관 정보
		
		// 1. 데이터 처리

		int pg = Integer.parseInt(request.getParameter("pg"));
		int theater_code = 0;

		// 극장코드 받아오기
		if (request.getParameter("seq") != null) {
			theater_code = Integer.parseInt(request.getParameter("seq"));
		}
		dto.setTheater_code(theater_code);
		
		// 파일명
		String fileName = uploadFile.getOriginalFilename();
		dto.setScreen_image1(fileName);
		
		
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
		Movie_theater_screen screen = dto.toEntity();
		// db 저장
		int result = service.screen_write(screen);
		System.out.println("result = " + result);
		
		// 좌석 정보
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			List<Map<String, Object>> seats = 
					objectMapper.readValue(seats_info, new TypeReference<List<Map<String, Object>>>() {});
			for (Map<String, Object> seat : seats) {
				int row = Integer.parseInt((String) seat.get("row"));
				int col = Integer.parseInt((String) seat.get("col"));
				int seatCode = Integer.parseInt((String) seat.get("seatCode"));
				String purchase = (String) seat.get("purchase");
		
				// DB에 저장할 좌석 정보 처리
				seat_service.screen_seat_save(dto.getScreen_num(), theater_code, row, col, seatCode,purchase);
			}
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		// DB에 저장하는 로직
		//screenService.saveScreenSeat(row, col, seatCode);
		
		// 2. 데이터 공유
		model.addAttribute("result", result);
		model.addAttribute("pg", pg);
		model.addAttribute("seq", theater_code);
		return "/theater/screen/screen_write";
	}
	
	// 상영관 목록
	@GetMapping("/theater/screen/screen_list")
	public String screen_list(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int screen_pg = 1;
		// 1) pg 저장
		if (request.getParameter("screen_pg") != null) {
			screen_pg = Integer.parseInt(request.getParameter("screen_pg"));
		}

		int theater_code = 0;
		if (request.getParameter("seq") != null) {
			theater_code = Integer.parseInt(request.getParameter("seq"));
		}
		

		// 2) 목록 가져오기
		int endNum = screen_pg * 5;
		int startNum = endNum - 4;
		
		List<Movie_theater_screen> list = service.screen_list(theater_code, startNum, endNum);
		//List<Movie_theater_screen> list = service.screen_list_test(startNum, endNum);
		
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
		model.addAttribute("seq", theater_code);
		
		// 3. view 파일명 리턴
		model.addAttribute("req", "/theater/screen/screen_list");
		return "/theater/theater_view";

	}
	
	// 상영관 보기
	@GetMapping("/theater/screen/screen_view")
	public String screen_view(Model model, HttpServletRequest request) {
		// 1. 데이터 처리

		int pg = Integer.parseInt(request.getParameter("pg"));
		int seq = Integer.parseInt(request.getParameter("seq"));
		int screen_pg = Integer.parseInt(request.getParameter("screen_pg"));
		int screen_num = Integer.parseInt(request.getParameter("screen_num"));
		int theater_code = Integer.parseInt(request.getParameter("theater_code"));
		

		// 상영관
		
		Movie_theater_screen screen = service.screen_view(screen_num, theater_code);
		int x_index_seat = screen.getX_index() * 2 - 1;
		String aisle = screen.getAisle();
		if (aisle != null && !aisle.isEmpty()) {
			String[] aisleValues = aisle.split(",");
			int[] aisle_values = new int[aisleValues.length];
			for (int i=0; i<aisleValues.length; i++) {
				aisle_values[i] = Integer.parseInt(aisleValues[i]);		
			}
			model.addAttribute("aisle_values", aisle_values);
		}
		
		// 좌석 정보
		// 리스트 가져오기
		List<Screen_seat> seat_list = seat_service.seat_list(screen_num, theater_code);
		for (int a=0; a<seat_list.size(); a++) {
			//System.out.println(seat_list.get(a));
		}	

		// 2. 데이터 공유
		model.addAttribute("screen_pg", screen_pg);
		model.addAttribute("screen_num", screen_num);
		model.addAttribute("theater_code", theater_code);
		model.addAttribute("screen", screen);
		model.addAttribute("x_index_seat", x_index_seat);
		model.addAttribute("seat_list", seat_list);
		
		model.addAttribute("pg", pg);
		model.addAttribute("seq", seq);

		return "/theater/screen/screen_view";
	}
	
	// 상영관 수정 폼
	@GetMapping("/theater/screen/screen_modifyForm")
	public String screen_modifyForm(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		
		// 상영관 정보
		int pg = Integer.parseInt(request.getParameter("pg"));
		int seq = Integer.parseInt(request.getParameter("seq"));
		int screen_pg = Integer.parseInt(request.getParameter("screen_pg"));
		int screen_num = Integer.parseInt(request.getParameter("screen_num"));
		String screen_name = request.getParameter("screen_name");
		int screen_cost = 0;
		if (request.getParameter("screen_cost") != null) {
			screen_cost = Integer.parseInt(request.getParameter("screen_cost"));
		}
		int theater_code = Integer.parseInt(request.getParameter("theater_code"));
		int x_index = Integer.parseInt(request.getParameter("x_index"));
		int y_index = Integer.parseInt(request.getParameter("y_index"));
		
		Movie_theater_screen screen = service.screen_view(screen_num, theater_code);
		if (request.getParameter("x_index") != null) {
			int x_index_seat = x_index*2 - 1;
			model.addAttribute("x_index_seat", x_index_seat);
		} else {
			int x_index_seat = screen.getX_index() * 2 - 1;
			model.addAttribute("x_index_seat", x_index_seat);
		}
		
		// 통로 정보
		if (request.getParameter("aisle") != null) {
			String aisle = request.getParameter("aisle");
			if (aisle != null && !aisle.isEmpty()) {
				String[] aisleValues = aisle.split(",");
				int[] aisle_values = new int[aisleValues.length];
				for (int i = 0; i < aisleValues.length; i++) {
					aisle_values[i] = Integer.parseInt(aisleValues[i]);
				}
				model.addAttribute("aisle_values", aisle_values);
			}	
		} else {
			System.out.println("ㅎㅇ Aisle값 없음");
			String aisle = screen.getAisle();
			System.out.println("Aisle = " + aisle);
			if (aisle != null && !aisle.isEmpty()) {
				String[] aisleValues = aisle.split(",");
				int[] aisle_values = new int[aisleValues.length];
				for (int i = 0; i < aisleValues.length; i++) {
					aisle_values[i] = Integer.parseInt(aisleValues[i]);
					System.out.println("aisle_values[i] = " + aisle_values[i]);
				}
				model.addAttribute("aisle_values", aisle_values);
			}
		}		
		
		// 좌석 정보
		// list 가져오기
		List<Screen_seat> seat_list = seat_service.seat_list(screen_num, theater_code);
		
		// 2. 데이터 공유
		model.addAttribute("screen_pg", screen_pg);
		model.addAttribute("screen_num", screen_num);
		model.addAttribute("screen_name", screen_name);
		model.addAttribute("screen_cost", screen_cost);
		model.addAttribute("theater_code", theater_code);
		model.addAttribute("x_index", x_index);
		model.addAttribute("y_index", y_index);
		model.addAttribute("screen", screen);
		model.addAttribute("seat_list", seat_list);
		
		model.addAttribute("pg", pg);
		model.addAttribute("seq", seq);

		return "/theater/screen/screen_modifyForm";
	}
	
	// 상영관 수정 처리
	@PostMapping("/theater/screen/screen_modify")
	public String screen_modify(Movie_theater_screen_DTO dto, Model model,
			HttpServletRequest request,
			@RequestParam("screen_img") MultipartFile uploadFile,
			@RequestParam("seats_info") String seats_info) {
		int pg = Integer.parseInt(request.getParameter("pg"));
		int seq = Integer.parseInt(request.getParameter("seq"));
		int screen_pg = Integer.parseInt(request.getParameter("screen_pg"));
		int screen_num = Integer.parseInt(request.getParameter("screen_num"));
		int theater_code = Integer.parseInt(request.getParameter("theater_code"));
		
		dto.setScreen_num(screen_num);
		String fileName = uploadFile.getOriginalFilename();
		dto.setScreen_image1(fileName);
		
		// entity 객체 생성
		Movie_theater_screen screen = dto.toEntity();
		
		int result = service.screen_modify(screen);
		System.out.println(dto);
		// 좌석 정보
		ObjectMapper objectMapper = new ObjectMapper();
		seat_service.screen_seat_delete(screen_num, theater_code);
		try {
			List<Map<String, Object>> seats = 
					objectMapper.readValue(seats_info, new TypeReference<List<Map<String, Object>>>() {});
			for (Map<String, Object> seat : seats) {
				int row = Integer.parseInt((String) seat.get("row"));
				int col = Integer.parseInt((String) seat.get("col"));
				int seatCode = Integer.parseInt((String) seat.get("seatCode"));
				String purchase = (String) seat.get("purchase");
		
				// DB에 저장할 좌석 정보 처리		
				seat_service.screen_seat_save(dto.getScreen_num(), theater_code, row, col, seatCode,purchase);
			}
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		
		// 2. 데이터 공유
		model.addAttribute("result", result);
		model.addAttribute("screen", screen);
		model.addAttribute("screen_pg", screen_pg);
		model.addAttribute("screen_num", screen_num);
		model.addAttribute("theater_code", theater_code);
		
		model.addAttribute("pg", pg);
		model.addAttribute("seq", seq);
		return "/theater/screen/screen_modify";
	}
	
	// 상영관 삭제
	@GetMapping("/theater/screen/screen_delete")
	public String screen_delete(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		int pg = Integer.parseInt(request.getParameter("pg"));
		int seq = Integer.parseInt(request.getParameter("seq"));		
		int screen_pg = Integer.parseInt(request.getParameter("screen_pg"));
		int screen_num = Integer.parseInt(request.getParameter("screen_num"));
		int theater_code = Integer.parseInt(request.getParameter("theater_code"));		
		
		int result = service.screen_delete(screen_num, theater_code);
		seat_service.screen_seat_delete(screen_num, theater_code);
		
		// 2. 데이터 공유
		model.addAttribute("result", result);
		model.addAttribute("screen_pg", screen_pg);
		model.addAttribute("screen_seq", screen_num);
		model.addAttribute("theater_code", theater_code);
		
		model.addAttribute("pg", pg);
		model.addAttribute("seq", seq);

		return "/theater/screen/screen_delete";
	}	
}
