package com.example.OnlyGuitars.dto;

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
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
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
    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
    public List<GuitarOutputDto> getGuitarList() {
        return guitarList;
    }
    public void setGuitarList(List<GuitarOutputDto> guitarList) {
        this.guitarList = guitarList;
    }
}



