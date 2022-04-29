package com.example.OnlyGuitars.service;

import com.example.OnlyGuitars.dto.GuitarInputDto;
import com.example.OnlyGuitars.dto.ProfileInputDto;
import com.example.OnlyGuitars.dto.ProfileOutputDto;
import com.example.OnlyGuitars.dto.ReviewInputDto;
import com.example.OnlyGuitars.model.Guitar;
import com.example.OnlyGuitars.model.Profile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProfileService {
    public void createProfile(ProfileInputDto profileInputDto);
    public void updateProfile(ProfileInputDto profileInputDto);
    public ProfileOutputDto getProfile();
    public void deleteProfile();
    public void addGuitarToProfile(GuitarInputDto guitarInputDto);
    public void removeGuitarFromList(GuitarInputDto guitarInputDto);
    public void removeGuitar(Long id, Guitar guitar);
    public void writeReview(@PathVariable Long id, @RequestBody ReviewInputDto reviewInputDto);
    public Profile toProfile(ProfileInputDto profileInputDto);
    public ProfileOutputDto fromProfile(Profile profile);
}
