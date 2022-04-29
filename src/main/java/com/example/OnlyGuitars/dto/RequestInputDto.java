package com.example.OnlyGuitars.dto;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class RequestInputDto {

    @NotBlank(message = "This field has to be filled in")
    public String brand;
    @NotBlank(message = "This field has to be filled in")
    public String model;
    public LocalDateTime timeStamp;


    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
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
