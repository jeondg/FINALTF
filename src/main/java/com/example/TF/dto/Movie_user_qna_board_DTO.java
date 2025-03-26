package com.example.TF.dto;

import java.util.Date;

import com.example.TF.entity.Movie_user_qna_board;

import lombok.Data;

@Data
public class Movie_user_qna_board_DTO {
    private int seq;        
    private String section;
    private String subject;
    private String content;
    private Date logtime;
    
    public Movie_user_qna_board toEntity() {
    	return new Movie_user_qna_board(seq, section, subject, content, logtime);
    }
}
