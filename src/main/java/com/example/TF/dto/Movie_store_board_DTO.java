package com.example.TF.dto;

import java.util.Date;

import com.example.TF.entity.Movie_store_board;

import lombok.Data;

@Data
public class Movie_store_board_DTO {
    private int seq;
    private String imageid;
    private String imagename;
    private int imageprice;
    private int imageqty;
    private String imagecontent;
    private String image1;
    private Date logtime;
    
    public Movie_store_board toEntity() {
    	return new Movie_store_board(seq, imageid, imagename, imageprice, imageqty, imagecontent, image1, logtime);
    }
}
