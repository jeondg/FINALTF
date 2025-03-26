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
public class Movie_info_stillcut {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "STILLCUT_SEQUENCE_GENERATOR")
	@SequenceGenerator(name = "STILLCUT_SEQUENCE_GENERATOR",
	sequenceName = "seq_movie_info_stillcut", initialValue = 1, allocationSize = 1)
    private int movie_stillcut_code;
    private int moviecode;
    private String stillcut_name;
    private String stillcut_src;
}
