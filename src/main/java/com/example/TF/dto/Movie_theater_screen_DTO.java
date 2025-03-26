package com.example.TF.dto;

import com.example.TF.entity.Movie_theater_screen;

import lombok.Data;

@Data
public class Movie_theater_screen_DTO {
    private int id;                	// 고유 ID
	private int screen_num;			// 상영관 코드
    private int theater_code;       // 극장 코드    
    private String screen_name;     // 상영관명
    private int screen_cost;        // 기본비용
    private String screen_image1;   // 상영관 사진 주소
    private int x_index;            // 가로 정보  
    private int y_index;            // 세로 정보
    private String aisle;			// 통로 위치  
    
    public Movie_theater_screen toEntity() {
    	return new Movie_theater_screen(id, screen_num, theater_code, screen_name, screen_cost, screen_image1, x_index, y_index, aisle);
    }
}
