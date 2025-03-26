package com.example.TF.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Screen_seat {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "SCREEN_SEAT_SEQUENCE_GENERATOR")
	@SequenceGenerator(name = "SCREEN_SEAT_SEQUENCE_GENERATOR",
	sequenceName = "screen_seat_id", initialValue = 1, allocationSize = 1)
	private int id;				   // 좌석 고유 ID
    private int screen_num;               // 상영관 코드
    private int theater_code;      // 극장 코드
    private int x_index;           // 좌석 가로 
    private int y_index;           // 좌석 세로 
    private int seat_code;         // 좌석분류 코드
    private String purchase;	   // 구매여부
}
