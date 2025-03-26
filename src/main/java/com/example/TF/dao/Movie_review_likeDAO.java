package com.example.TF.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.example.TF.dto.Movie_review_likeDTO;
import com.example.TF.entity.Movie_review_like;
import com.example.TF.repository.Movie_review_likeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class Movie_review_likeDAO {

    @Autowired
    Movie_review_likeRepository likeRepository;

    @PersistenceContext
    private EntityManager em;

    // 좋아요 존재여부 체크
    public boolean existsLike(int reviewcode, String user_id) {
        return likeRepository.existsByReviewcodeAndUserId(reviewcode, user_id) > 0;
    }

    // 좋아요 추가
    public Movie_review_like insertLike(Movie_review_likeDTO dto) {
        Movie_review_like entity = dto.toEntity();
        return likeRepository.save(entity);
    }


    // 좋아요 개수
    public int countLike(int reviewcode) {
        return likeRepository.countByReviewcode(reviewcode);
    }

    // 좋아요 삭제
    public void removeLike(int reviewcode, String user_id) {
        likeRepository.deleteByReviewcodeAndUserId(reviewcode, user_id);
    }
}
