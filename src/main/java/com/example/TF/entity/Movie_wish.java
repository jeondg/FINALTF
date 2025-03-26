package com.example.TF.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Movie_wish {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, 
    generator = "WISH_SEQ_GEN")
    @SequenceGenerator(name = "WISH_SEQ_GEN", sequenceName = "seq_movie_wish", 
    initialValue = 1, allocationSize = 1)
    private int wishcode; 

    private int moviecode;
    @Column(name = "user_id")
    private String userId;
}