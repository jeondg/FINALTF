package com.example.TF.dto;

import com.example.TF.entity.Screen_seat;

import lombok.Data;

@Data
public class Screen_seat_DTO {
	private int id;				   // 좌석 고유 ID
    private int screen_num;               // 상영관 코드
    private int theater_code;      // 극장 코드
    private int x_index;           // 좌석 가로 
    private int y_index;           // 좌석 세로 
    private int seat_code;         // 좌석분류 코드
    private String purchase;	   // 구매여부
    
    public Screen_seat toEntity() {
    	return new Screen_seat(id, screen_num, theater_code, x_index, y_index, seat_code, purchase);
    }
}
