package com.example.TF.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.TF.dto.Movie_reviewDTO;
import com.example.TF.dto.Movie_review_likeDTO;
import com.example.TF.entity.Movie_review;

import com.example.TF.service.Movie_service;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/movieinfo")
public class Movie_reviewController {

    @Autowired
    private Movie_service service;
    
    //답글
    @GetMapping("/review")
    public List<Movie_reviewDTO> getReviewList(
        @RequestParam("moviecode") int moviecode,
        @RequestParam("start") int start,
        @RequestParam("end") int end,
        @RequestParam(name = "sort", defaultValue = "latest") String sort
    ) {
        if (sort.equals("like")) {
            return service.review_list_by_like(moviecode, start, end);
        } else {
            return service.review_list_with_grade_paging(moviecode, start, end);
        }
    }
    
    @GetMapping("/review/count")
    public int getReviewCount() {
        return service.review_count();
    }
    
    @PostMapping("/review")
    public Movie_review writeReview(@RequestBody Movie_reviewDTO dto) {
    	dto.setLogtime(new Date());
    	System.out.println("제발 " + dto.getLogtime());
        return service.writereview(dto);
    }    
    
    //**HTTP 메서드(GET, POST 등)**가 다르면 같은 URL이어도 전혀 다른 요청으로 처리된다!
    
    @GetMapping("/review/check")
    public boolean checkDuplicateReview(@RequestParam("moviecode") int moviecode,
                                        HttpSession session) {
        String userId = (String) session.getAttribute("memId");
        if (userId == null) return false;

        return service.checkReviewExists(moviecode, userId);
    }
    
    // 리뷰 수정 API
    @PutMapping("/review/{reviewcode}")
    public ResponseEntity<String> updateReview(@PathVariable("reviewcode") int reviewcode,
                                               @RequestBody Movie_reviewDTO dto,
                                               HttpSession session) {
        String userId = (String) session.getAttribute("memId");
        // 권한 체크
        Movie_review existing = service.getReviewByCode(reviewcode);
        if (existing == null || !existing.getUser_id().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한 없음");
        }

        existing.setReview_comment(dto.getReview_comment());
        existing.setRating(dto.getRating());
        existing.setLogtime(new Date());

        service.updateReview(existing);
        return ResponseEntity.ok("수정 완료");
    }

    // 리뷰 삭제 API
    @DeleteMapping("/review/{reviewcode}")
    public ResponseEntity<String> deleteReview(@PathVariable("reviewcode") int reviewcode,
                                               HttpSession session) {
        String userId = (String) session.getAttribute("memId");
        Movie_review existing = service.getReviewByCode(reviewcode);
        if (existing == null || !existing.getUser_id().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한 없음");
        }

        service.deleteReview(reviewcode);
        return ResponseEntity.ok("삭제 완료");
    }
    
    // 좋아요 추가
    @PostMapping("/review/like")
    public ResponseEntity<?> addLike(@RequestBody Movie_review_likeDTO dto, HttpSession session) {
        dto.setUserId((String) session.getAttribute("memId"));
        if (dto.getUserId() == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");

        boolean added = service.addLike(dto);
        if (added) {
            return ResponseEntity.ok("좋아요 성공");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 좋아요한 리뷰입니다.");
        }
    }

    // 좋아요 개수 조회
    @GetMapping("/review/{reviewcode}/likes")
    public int getLikeCount(@PathVariable("reviewcode") int reviewcode) {
        return service.getLikeCount(reviewcode);
    }

    // 좋아요 취소
    @DeleteMapping("/review/{reviewcode}/like")
    public ResponseEntity<?> removeLike(@PathVariable("reviewcode") int reviewcode, HttpSession session) {
        String user_id = (String) session.getAttribute("memId");
        if (user_id == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");

        service.removeLike(reviewcode, user_id);
        return ResponseEntity.ok("좋아요 취소됨");
    }

}
