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
public class Movie_theater {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "MOVIE_THEATER_SEQUENCE_GENERATOR")
	@SequenceGenerator(name = "MOVIE_THEATER_SEQUENCE_GENERATOR",
	sequenceName = "seq_movie_theater", initialValue = 1, allocationSize = 1)
    private int seq;            	// 극장 코드
    private String theater_name;    // 극장명
    private String theater_image1;   // 극장사진
    private String theater_phone;     // 극장 연락처
}
