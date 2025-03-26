package com.example.TF.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.TF.dto.Movie_MemberDTO;
import com.example.TF.dto.Movie_infoDTO;
import com.example.TF.entity.Movie_Member;
import com.example.TF.entity.Movie_info;
import com.example.TF.entity.Movie_admin_board;
import com.example.TF.entity.Movie_user_qna_board;
import com.example.TF.service.Movie_service;

import jakarta.servlet.http.HttpSession;

@Controller
public class Movie_MemberController {
	@Autowired
	Movie_service service;
	
	@GetMapping("/customerServiceMain")
	public String customerServiceMain(Model model, HttpSession session) {
        
		if (session.getAttribute("memId") != null) {
            model.addAttribute("memId", session.getAttribute("memId"));
        }		
		int pg = 1;
		List<Movie_admin_board> list1 = service.admin_board_list(1, 5);
		List<Movie_user_qna_board> list2 = service.qna_list(1, 5);
		// 2. 데이터 공유
		model.addAttribute("pg", pg);
		model.addAttribute("req2", "none");
		model.addAttribute("list1", list1);
		model.addAttribute("list2", list2);
		// 3. view 파일명 리턴
		return "/main/customerServiceMain";
		
	}
	
	@GetMapping("/mypageMain")	// mypage가기
	public String mypageMain(Model model, Movie_MemberDTO dto, HttpSession session) {
		// 1. 데이터 처리
		String id = (String)session.getAttribute("memId");
		// 회원정보 읽기
		Movie_Member member = service.member_getMember(id);
		// 2. 데이터 공유
		model.addAttribute("member", member);
		model.addAttribute("req", "none");
		return "/main/mypageMain";
	}
	// http://localhost:8080/main
	@GetMapping("/main")
	public String main(HttpSession session, Model model, Movie_infoDTO dto) {
	    String memId = (String) session.getAttribute("memId");
	    if (memId != null) {
	        model.addAttribute("memId", memId);
	    }

	    List<Movie_info> list_boxoffice = service.info_list_boxoffice(1, 4);
	    List<Movie_info> list_total = service.info_list_total(1, 4);
	    List<Movie_info> list_korea = service.info_list_country(1, 4);
	    List<Movie_info> list_foreign = service.info_list_foreign(1, 4);

	    // 찜한 영화 코드들 한 번에 조회
	    Set<Integer> wishedMovieCodes = new HashSet<>();
	    if (memId != null) {
	        model.addAttribute("memId", memId);
	        wishedMovieCodes = service.getWishedMovieCodes(memId);
	    }

	    applyWishData(list_boxoffice, wishedMovieCodes);
	    applyWishData(list_total, wishedMovieCodes);
	    applyWishData(list_korea, wishedMovieCodes);
	    applyWishData(list_foreign, wishedMovieCodes);

	    model.addAttribute("list_boxoffice", list_boxoffice);
	    model.addAttribute("list_total", list_total);
	    model.addAttribute("list_korea", list_korea);
	    model.addAttribute("list_foreign", list_foreign);

	    return "/main/main";
	}
	private void applyWishData(List<Movie_info> list, Set<Integer> wishedCodes) {
	    for (Movie_info movie : list) {
	        int code = movie.getMoviecode();
	        movie.setWished(wishedCodes.contains(code)); // 로그인 안했으면 false
	        movie.setWishCount(service.getMovieWishCount(code));
	    }
	}
	
	// http://localhost:8080/member/loginForm
	@GetMapping("/member/loginForm")	// 로그인폼
	public String loginForm() {
		return "/member/loginForm";
	}
	
	@PostMapping("/member/login")
	public String login(Movie_MemberDTO dto, HttpSession session) {
		// db
		Movie_Member member = service.member_login(dto.getId(), dto.getPwd());
		// 2. 데이터 공유
		// 3. view 파일명 리턴
		if(member != null) { // 로그인 성공
			session.setAttribute("memId", member.getId());
			session.setAttribute("memName", member.getName());
			session.setAttribute("loggedIn", true);
			return "/member/loginOK";	// /member/loginOK.html
		} else {			// 로그인 실패
			
		}
		return "/member/loginFail";	// /member/loginFail.html
	}
	
	@GetMapping("/member/logout")
	public String logout(HttpSession session) {
		// 1. 데이터 처리
		session.removeAttribute("memId");
		session.removeAttribute("memName");
		session.removeAttribute("loggedIn");
		// 2. 데이터 공유
		// 3. view 파일명 리턴
		return "/member/logout"; // /member/logout.html
	}	
	
	@GetMapping("/member/writeForm")
	public String writeForm() {
		// 1. 데이터 처리
		// 2. 데이터 공유
		// 3. view 파일명 리턴
		return "/member/writeForm"; // /member/writeForm.html
	}	
	
	@PostMapping("/member/write")
	public String write(Movie_MemberDTO dto, Model model) {
		// 1. 데이터 처리
		dto.setLogtime(new Date());
		dto.setGrade("basic");
		dto.setVippoint(0);
		dto.setPoint(0);
		// db
		Movie_Member member = service.member_write(dto);
		// 2. 데이터 공유
		model.addAttribute("member",member);
		// 3. view 파일명 리턴
		return "/member/write"; // /member/write.html
	}
	
	@GetMapping("/member/checkId")
	public String checkId(@RequestParam("id") String id, Model model) {
		// 1. 데이터 처리
		// db
		boolean result = service.member_isExistId(id);
		// 2. 데이터 공유
		model.addAttribute("id", id);
		model.addAttribute("result", result);
		// 3. view 파일명 리턴
		return "/member/checkId"; // /member/checkId
	}
	
	@GetMapping("/member/modifyForm")
	public String modifyForm(HttpSession session, Model model) {
		// 1. 데이터 처리
		String id = (String)session.getAttribute("memId");
		// 회원정보 읽기
		Movie_Member member = service.member_getMember(id);
		// 2. 데이터 공유
		model.addAttribute("member", member);
		model.addAttribute("req", "/member/modifyForm");
		// 3. view 파일명 리턴
		return "/main/mypageMain"; 
	}
	
	@PostMapping("/member/modify")
	public String modify(Movie_MemberDTO dto, Model model) {
		// 1. 데이터 처리		
		// db
		boolean result = service.member_modify(dto);
		System.out.println("result = "+ result);
		// 2. 데이터 공유
		model.addAttribute("result", result);
		// 3. view 파일명 리턴
		return "/member/modify"; // /member/modify.html
	}
	
	@PostMapping("/member/delete")
	public String delete(HttpSession session , Model model) {
	    // 1. 세션에서 id 먼저 가져오기
	    String id = (String) session.getAttribute("memId");
	    
	    if(id != null) {
		    // 서비스 호출해서 탈퇴 처리
		    String result = service.member_delete((String) session.getAttribute("memId"));
	        if ("삭제성공".equals(result)) {
	            session.invalidate(); // 세션 완전 초기화
				// 아래것들 한번에
				//session.removeAttribute("memId");
				//session.removeAttribute("memName");
			    model.addAttribute("result",result);
	        }	

			return "/member/delete";
	    }
	    // id가 없을 때 (예외 처리)
	    model.addAttribute("result", "탈퇴 실패: 로그인 정보가 없습니다.");
	    return "/member/delete";	    

	}
}
