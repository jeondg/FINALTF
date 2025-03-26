package com.example.TF.dto;

import java.util.Date;

import com.example.TF.entity.Movie_admin_board;

import lombok.Data;

@Data
public class Movie_admin_board_DTO {
    private int seq;             
    private String section;
    private String subject;
    private String content;
    private Date logtime;
    
    public Movie_admin_board toEntity() {
    	return new Movie_admin_board(seq, section, subject, content, logtime);
    }
}
