package com.example.TF.dto;

import com.example.TF.entity.Movie_theater;

import lombok.Data;

@Data
public class Movie_theater_DTO {
    private int seq;            	// 극장 코드
    private String theater_name;    // 극장명
    private String theater_image1;   // 극장사진
    private String theater_phone;     // 극장 연락처
    
    public Movie_theater toEntity() {
    	return new Movie_theater(seq, theater_name, theater_image1, theater_phone);
    }
}
