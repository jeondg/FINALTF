package com.example.TF.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.TF.entity.Movie_review_like;

public interface Movie_review_likeRepository extends JpaRepository<Movie_review_like, Integer> {

    @Query(value = "SELECT COUNT(*) FROM movie_review_like WHERE reviewcode = :reviewcode", nativeQuery = true)
    int countByReviewcode(@Param("reviewcode") int reviewcode);

    @Query(value = "SELECT COUNT(*) FROM movie_review_like WHERE reviewcode = :reviewcode AND user_id = :user_id", nativeQuery = true)
    int existsByReviewcodeAndUserId(@Param("reviewcode") int reviewcode, @Param("user_id") String user_id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM movie_review_like WHERE reviewcode = :reviewcode AND user_id = :user_id", nativeQuery = true)
    void deleteByReviewcodeAndUserId(@Param("reviewcode") int reviewcode, @Param("user_id") String user_id);
}
