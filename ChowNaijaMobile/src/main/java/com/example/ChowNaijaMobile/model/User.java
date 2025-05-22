package com.example.ChowNaijaMobile.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ChownaijaUsers")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    private String password;



}
