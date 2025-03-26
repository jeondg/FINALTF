package com.example.TF.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TF.dto.Movie_wishDTO;
import com.example.TF.service.Movie_service;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/movieinfo/wish")
public class Movie_wishController {

    @Autowired
    private Movie_service service;

    // 찜 수 조회
    @GetMapping("/{moviecode}/count")
    public int getWishCount(@PathVariable("moviecode") int moviecode) {
        return service.getMovieWishCount(moviecode);
    }

    // 찜 여부 확인
    @GetMapping("/{moviecode}/check")
    public boolean checkWish(@PathVariable("moviecode") int moviecode, HttpSession session) {
        String userId = (String) session.getAttribute("memId");
        if (userId == null) return false;
        return service.isMovieWished(moviecode, userId);
    }

 // 찜 추가
    @PostMapping("/{moviecode}")
    public ResponseEntity<?> addWish(@PathVariable("moviecode") int moviecode, HttpSession session) {
        String userId = (String) session.getAttribute("memId");
        if (userId == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");

        Movie_wishDTO dto = new Movie_wishDTO();
        dto.setWishcode(0); // 자동 생성
        dto.setMoviecode(moviecode);
        dto.setUser_id(userId);
        
        service.addMovieWish(dto);

        int newCount = service.getMovieWishCount(moviecode); // 추가된 후 count 조회

        return ResponseEntity.ok().body(
            java.util.Map.of(
                "status", "찜 추가 완료",
                "wishCount", newCount
            )
        );
    }

    // 찜 취소
    @Transactional
    @DeleteMapping("/{moviecode}")
    public ResponseEntity<?> removeWish(@PathVariable("moviecode") int moviecode, HttpSession session) {
        String userId = (String) session.getAttribute("memId");
        if (userId == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");

        service.removeMovieWish(moviecode, userId);

        int newCount = service.getMovieWishCount(moviecode); // 취소된 후 count 조회

        return ResponseEntity.ok().body(
            java.util.Map.of(
                "status", "찜 취소 완료",
                "wishCount", newCount
            )
        );
    }
    
    
}
