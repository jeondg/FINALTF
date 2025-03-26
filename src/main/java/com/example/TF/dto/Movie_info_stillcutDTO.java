package com.example.TF.dto;

import com.example.TF.entity.Movie_info_stillcut;

import lombok.Data;

@Data
public class Movie_info_stillcutDTO {
    private int movie_stillcut_code;
    private int moviecode;
    private String stillcut_name;
    private String stillcut_src;
    
    public Movie_info_stillcut toEntity() {
    	return new Movie_info_stillcut(movie_stillcut_code, moviecode, stillcut_name, stillcut_src);
    }
}
