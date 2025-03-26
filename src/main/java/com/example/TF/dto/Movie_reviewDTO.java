package com.example.TF.dto;

import java.util.Date;

import com.example.TF.entity.Movie_review;

import lombok.Data;


@Data
public class Movie_reviewDTO {
    private int reviewcode;
    private int moviecode;
    private String user_id;
    private String review_comment;
    private int rating;
    private Date logtime;
    private String grade;
    public Movie_review toEntity() {
    	return new Movie_review(reviewcode, moviecode, user_id, review_comment, rating, logtime);
    }
}
