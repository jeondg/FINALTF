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
public class Movie_theater_screen {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "MOVIE_THEATER_SCREEN_SEQUENCE_GENERATOR")
	@SequenceGenerator(name = "MOVIE_THEATER_SCREEN_SEQUENCE_GENERATOR",
	sequenceName = "id_movie_theater_screen", initialValue = 1, allocationSize = 1)
    private int id;                	// 고유 ID
	private int screen_num;			// 상영관 코드
    private int theater_code;       // 극장 코드    
    private String screen_name;     // 상영관명
    private int screen_cost;        // 기본비용
    private String screen_image1;   // 상영관 사진 주소
    private int x_index;            // 가로 정보  
    private int y_index;            // 세로 정보
    private String aisle;			// 통로 위치
}
