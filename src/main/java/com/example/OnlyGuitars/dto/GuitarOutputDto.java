package com.example.OnlyGuitars.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Lob;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GuitarOutputDto extends StatusOutput{
    public Long id;
    public String brand;
    public String model;
    @JsonIgnore
    @Lob
    public byte[] image;
    public String imageApi;
    public LocalDateTime timeStamp;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public List<ReviewOutputDto> reviewList = new ArrayList<>();
    public int reviewListSize;
    public int profileLikes;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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
    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<ReviewOutputDto> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<ReviewOutputDto> reviewList) {
        this.reviewList = reviewList;
    }

    public int getReviewListSize() {
        return reviewListSize;
    }
    public void setReviewListSize(int reviewListSize) {
        this.reviewListSize = reviewListSize;
    }
}