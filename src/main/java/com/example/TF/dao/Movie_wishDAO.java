package com.example.TF.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.TF.dto.Movie_wishDTO;
import com.example.TF.entity.Movie_wish;
import com.example.TF.repository.Movie_wishRepository;

@Repository
public class Movie_wishDAO {

    @Autowired
    Movie_wishRepository movie_wishrepository;

    // 찜 여부 확인
    public boolean isWished(int moviecode, String user_id) {
        return movie_wishrepository.existsByMoviecodeAndUserId(moviecode, user_id);
    }

    // 찜 추가
    public void addWish(Movie_wishDTO dto) {
    	movie_wishrepository.save(dto.toEntity());
    }

    // 찜 취소
    public void removeWish(int moviecode, String user_id) {
    	movie_wishrepository.deleteByMoviecodeAndUserId(moviecode, user_id);
    }

    // 찜 수 조회
    public int getWishCount(int moviecode) {
        return movie_wishrepository.countByMoviecode(moviecode);
    }
    
    public List<Movie_wish> findByUserId(String userId) {
        return movie_wishrepository.findByUserId(userId);
    }
}
