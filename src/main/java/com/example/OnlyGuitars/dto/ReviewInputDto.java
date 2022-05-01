package com.example.OnlyGuitars.dto;

import com.example.OnlyGuitars.model.Guitar;
import com.example.OnlyGuitars.model.Profile;
import com.example.OnlyGuitars.model.Review;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ReviewInputDto {

    public Long id;
    @NotBlank(message = "Title can't be empty")
    public String title;
    @NotBlank(message = "Your review is empty")
    @Size(min = 50, max = 200)
    public String details;
    public Guitar guitar;
    public Profile profile;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public String getDetails() {
        return details;
    }
    public Guitar getGuitar() {
        return guitar;
    }
    public void setGuitar(Guitar guitar) {
        this.guitar = guitar;
    }
    public Profile getProfile() {
        return profile;
    }
    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
