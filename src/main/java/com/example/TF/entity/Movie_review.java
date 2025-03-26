package com.example.TF.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Movie_review {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
			generator = "MOVIE_REVIEW_SEQUENCE_GENERATOR")
	@SequenceGenerator(name = "MOVIE_REVIEW_SEQUENCE_GENERATOR",
			sequenceName = "seq_movie_review", initialValue = 1,
			allocationSize = 1)
    private int reviewcode;	
    private int moviecode;
    private String user_id;
    private String review_comment;
    private int rating;
    private Date logtime;
	
	
}
