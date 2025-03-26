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
public class Movie_user_qna_board {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "MOVIE_USER_QNA_BOARD_SEQUENCE_GENERATOR")
	@SequenceGenerator(name = "MOVIE_USER_QNA_BOARD_SEQUENCE_GENERATOR",
	sequenceName = "seq_movie_user_qna_board", initialValue = 1, allocationSize = 1)
    private int seq;        
    private String section;
    private String subject;
    private String content;
    @Temporal(TemporalType.DATE)
    private Date logtime;
}
