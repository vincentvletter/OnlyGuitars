package com.example.OnlyGuitars.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Lob;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GuitarOutputDto {

    public Long id;
    public String brand;
    public String model;
    @JsonIgnore
    @Lob
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public byte[] image;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public String imageApi;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
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

    public void setReviewList(List<ReviewOutputDto> reviewList) {
        this.reviewList = reviewList;
    }
}