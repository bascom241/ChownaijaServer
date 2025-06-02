package com.example.ChowNaijaMobile.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ChownaijaUsers")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private Boolean isAccountFlaged = false;
    private LocalDateTime flagDate = null;

    public LocalDateTime getFlagDate() {
        return flagDate;
    }

    public void setFlagDate(LocalDateTime flagDate) {
        this.flagDate = flagDate;
    }

    public Boolean getAccountFlaged() {
        return isAccountFlaged;
    }

    public void setAccountFlaged(Boolean accountFlaged) {
        isAccountFlaged = accountFlaged;
    }

    public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
