package com.example.OnlyGuitars.dto;

import java.time.LocalDateTime;


public class RequestOutputDto {

    public Long id;
    public String brand;
    public String model;
    public LocalDateTime timeStamp;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
