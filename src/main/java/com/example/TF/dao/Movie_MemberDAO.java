package com.example.TF.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.TF.dto.Movie_MemberDTO;
import com.example.TF.entity.Movie_Member;
import com.example.TF.repository.Movie_MemberRepository;

@Repository
public class Movie_MemberDAO {
	@Autowired
	Movie_MemberRepository movie_MemberRepository;
	
	// 로그인
	public Movie_Member login(String id, String pwd) {
		return movie_MemberRepository.findByIdAndPwd(id, pwd); 
	}
	// 회원정보 저장
	public Movie_Member write(Movie_MemberDTO dto) {
		dto.setLogtime(new Date());
		dto.setGrade("basic");
		dto.setVippoint(0);
		dto.setPoint(0);
		Movie_Member movie_member = dto.toEntity(); // entity로 변경
		// 기존 데이터 있는지 확인
		boolean isExist = movie_MemberRepository.existsById(movie_member.getId());
		if(!isExist) {
			// 없을때 저장
			return movie_MemberRepository.save(movie_member);
		}
		return null;
	}
	
	// id 중복검사
	public boolean isExistId(String id) {
		return movie_MemberRepository.existsById(id);
	}
	
	// 1명의 회원정보읽기
	public Movie_Member getMember(String id) {
		return movie_MemberRepository.findById(id).orElse(null);
	}
	
	// 수정하기
	public boolean modify(Movie_MemberDTO dto) {
		// 1명 데이터읽기
		Movie_Member member_old = movie_MemberRepository.findById(dto.getId()).orElse(null);
		boolean result = false;
		if(member_old != null) {
			// 존재하면 dto를 entity로 변경
			dto.setGrade(member_old.getGrade());
			dto.setVippoint(member_old.getVippoint());
			dto.setPoint(member_old.getPoint());
			dto.setLogtime(member_old.getLogtime());
			Movie_Member member = dto.toEntity();
			
			// 저장
			Movie_Member member_result = movie_MemberRepository.save(member);
			if(member_result != null) result = true;
		}
		return result;
	}
	
	// 탈퇴하기
    public String deleteMember(String id) {
    	String result = ""; 
        if (movie_MemberRepository.existsById(id)) { 
            movie_MemberRepository.deleteById(id);
            result = "삭제성공";
        } else {
        	result = "삭제실패";
        }
        return result;
    }
}
