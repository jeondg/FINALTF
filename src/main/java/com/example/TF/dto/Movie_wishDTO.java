package com.example.TF.dto;

import com.example.TF.entity.Movie_wish;

import lombok.Data;

@Data
public class Movie_wishDTO {
	private int wishcode;
    private int moviecode;
    private String user_id;

    public Movie_wish toEntity() {
        return new Movie_wish(wishcode, moviecode, user_id);  
    }
}
