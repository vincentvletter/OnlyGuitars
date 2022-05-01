package com.example.OnlyGuitars.dto;

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
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }



}
