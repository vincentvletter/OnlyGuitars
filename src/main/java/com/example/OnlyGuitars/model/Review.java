package com.example.OnlyGuitars.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.jfr.Timestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String details;
    @Timestamp
    private LocalDateTime timeStamp = LocalDateTime.now();
    
    @ManyToOne
    @JoinColumn(name = "guitar_id")
    @JsonIgnore
    private Guitar guitar;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
    public Guitar getGuitar() {
        return guitar;
    }
    public void setGuitar(Guitar guitar) {
        this.guitar = guitar;
    }
    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
