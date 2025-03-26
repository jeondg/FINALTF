package com.example.TF.dto;

import com.example.TF.entity.Movie_review_like;

import lombok.Data;

@Data
public class Movie_review_likeDTO {
    private int likecode;  // DB에서 받아온 값으로 세팅
    private int reviewcode;
    private String userId;

    public Movie_review_like toEntity() {
        return new Movie_review_like(likecode, reviewcode, userId);
    }
}
