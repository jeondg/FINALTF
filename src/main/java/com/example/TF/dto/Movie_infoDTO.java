package com.example.TF.dto;

import java.util.Date;

import com.example.TF.entity.Movie_info;

import lombok.Data;

@Data
public class Movie_infoDTO {
    private int moviecode;
    private String poster1;
    private String country;
    private String title;
    private String synopsis;
    private Date releasedate;
    private int runningtime;
    private int agerating;
    private String genre;
    private String director;
    private String actors;
    private int yesterday;
    private int total;
    private String trailer;
    
    public Movie_info toEntity() {
    	return new Movie_info(moviecode, poster1, country, title, synopsis, releasedate, runningtime, agerating, genre, director, actors, yesterday, total, trailer);
    }
}
