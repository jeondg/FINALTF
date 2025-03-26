package com.example.TF.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie_question_board {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "MOVIE_QUESTION_BOARD_SEQUENCE_GENERATOR")
	@SequenceGenerator(name = "MOVIE_QUESTION_BOARD_SEQUENCE_GENERATOR",
	sequenceName = "seq_movie_question_board", initialValue = 1, allocationSize = 1)
    private int seq;
    private String question_id;
    private String question_type;
    private String question_stat;
    private String subject;
    private String question_content;
    private String answer_content;
    private String image1;
    @Temporal(TemporalType.DATE)
    private Date logtime;
}
