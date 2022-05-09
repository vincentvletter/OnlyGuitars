package com.example.OnlyGuitars.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;



public class ProfileInputDto {

    public Long id;
    @NotBlank
    @Size(min = 3, max = 50, message = "username has to be at least 3 and maximum 50 characters")
    public String username;
    @NotBlank
    @Size(min = 6, message = "password has to be at least 6 characters")
    public String password;
    public int enabled = 1;


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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
