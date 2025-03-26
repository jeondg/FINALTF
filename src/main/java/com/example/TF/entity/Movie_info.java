package com.example.TF.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
public class Movie_info {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
			generator = "MOVIE_INFO_SEQUENCE_GENERATOR")
	@SequenceGenerator(name = "MOVIE_INFO_SEQUENCE_GENERATOR",
			sequenceName = "seq_movie_info", initialValue = 1,
			allocationSize = 1)
    private int moviecode;
	private String poster1;
	private String country;
    private String title;
    private String synopsis;
    @Temporal(TemporalType.DATE)
    private Date releasedate;
    private int runningtime;
    private int agerating;
    private String genre;
    private String director;
    private String actors;
    private int yesterday;
    private int total;
    private String trailer;
    @Transient
    private boolean wished;

    @Transient
    private int wishCount;
    
	public Movie_info(int moviecode, String poster1, String country, String title, String synopsis,
            Date releasedate, int runningtime, int agerating, String genre,
            String director, String actors,int yesterday, int total, String trailer) {
	this.moviecode = moviecode;
	this.poster1 = poster1;
	this.country = country;
	this.title = title;
	this.synopsis = synopsis;
	this.releasedate = releasedate;
	this.runningtime = runningtime;
	this.agerating = agerating;
	this.genre = genre;
	this.director = director;
	this.actors = actors;
	this.yesterday = yesterday;
	this.total = total;
	this.trailer = trailer;
}    
}
