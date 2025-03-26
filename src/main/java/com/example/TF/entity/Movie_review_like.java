package com.example.TF.entity;

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
public class Movie_review_like {

    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "MOVIE_REVIEW_LIKE_SEQUENCE_GENERATOR")
    @SequenceGenerator(name = "MOVIE_REVIEW_LIKE_SEQUENCE_GENERATOR",
	sequenceName = "seq_movie_review_like", initialValue = 1,
	allocationSize = 1)
    private int likecode;

    private int reviewcode;
    private String user_id;
}
