package com.example.TF.dto;

import java.util.Date;

import com.example.TF.entity.Movie_Member;

import lombok.Data;

@Data
public class Movie_MemberDTO {
    private String id;
    private String name;
    private String pwd;
    private String gender;
    private String email1;
    private String email2;
    private String tel1;
    private String tel2;
    private String tel3;
    private String addr;
    private String grade;
    private int vippoint;
    private int point;
    private Date logtime;
    
    public Movie_Member toEntity() {
    	return new Movie_Member(id, name, pwd, gender, email1, email2, tel1, tel2, tel3, addr, grade, vippoint, point, logtime);
    }
}
