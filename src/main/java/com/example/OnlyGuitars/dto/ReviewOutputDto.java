package com.example.OnlyGuitars.dto;

import com.example.OnlyGuitars.model.Review;

import java.time.LocalDateTime;

public class ReviewOutputDto extends StatusOutput {
    public Long id;
    public String title;
    public String details;
    public LocalDateTime timeStamp;


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



}
