package com.example.OnlyGuitars.model;

import jdk.jfr.Timestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private int enabled;
    @Timestamp
    private LocalDateTime timeStamp = LocalDateTime.now();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany
    private List<Guitar> guitars = new ArrayList<>();

    @OneToOne(
            targetEntity = Authority.class,
            mappedBy = "profile",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Authority authority;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getEnabled() {
        return enabled;
    }
    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
    public List<Guitar> getGuitars() {
        return guitars;
    }
    public List<Review> getReviews() {
        return reviews;
    }
    public Authority getAuthority() {
        return authority;
    }
    public void setAuthority(Authority authority) {
        this.authority = authority;
    }
}