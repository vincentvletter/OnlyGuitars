package com.example.OnlyGuitars.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ProfileOutputDto extends StatusOutput {

    public Long id;
    public String username;
    public String role;
    public int enabled;
    public LocalDateTime timeStamp;
    public List<GuitarOutputDto> guitarList = new ArrayList<>();
    @JsonIgnore
    public AuthorityOutputDto authority;


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
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
    public void setGuitarList(List<GuitarOutputDto> guitarList) {
        this.guitarList = guitarList;
    }
}



