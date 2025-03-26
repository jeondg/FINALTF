package com.example.TF.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie_Member {
	@Id
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
	@Temporal(TemporalType.DATE)// 년월일 저장 설정
	private Date logtime;
}
