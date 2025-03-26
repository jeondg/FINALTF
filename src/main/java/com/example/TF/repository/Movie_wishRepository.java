package com.example.TF.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TF.entity.Movie_wish;

public interface Movie_wishRepository extends JpaRepository<Movie_wish, Integer> {
    
    // 유저가 특정 영화 찜했는지
    boolean existsByMoviecodeAndUserId(int moviecode, String userId);

    // 찜 취소
    void deleteByMoviecodeAndUserId(int moviecode, String userId);

    // 영화별 찜 총 수
    int countByMoviecode(int moviecode);
    
    List<Movie_wish> findByUserId(String userId);
}
