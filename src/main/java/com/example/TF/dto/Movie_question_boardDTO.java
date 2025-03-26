package com.example.TF.dto;

import java.util.Date;

import com.example.TF.entity.Movie_question_board;

import lombok.Data;

@Data
public class Movie_question_boardDTO {
    private int seq;
    private String question_id;
    private String question_type;
    private String question_stat;
    private String subject;
    private String question_content;
    private String answer_content;
    private String image1;
    private Date logtime;
    
    public Movie_question_board toEntity() {
    	return new Movie_question_board(seq, question_id, question_type, question_stat, subject, question_content, answer_content, image1, logtime);
    }
}
