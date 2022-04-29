package com.example.OnlyGuitars.controller;

import com.example.OnlyGuitars.dto.*;
import com.example.OnlyGuitars.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
public class ProfileController {

    @Autowired
    ProfileService profileService;


    @PostMapping("/register")
    public ResponseEntity<Object> createProfile(@Validated @RequestBody ProfileInputDto profileInputDto, BindingResult bindingResult) {
        StatusOutput statusOutput = new StatusOutput();
        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                statusOutput.getErrorList().add(fe.getDefaultMessage());
            }
            statusOutput.setSucceded(false);
            return new ResponseEntity<>(statusOutput, HttpStatus.BAD_REQUEST);
        }
        try {
            profileService.createProfile(profileInputDto);
            statusOutput.setSuccededMessage("profiel aangemaakt");
            statusOutput.setSucceded(true);
            return new ResponseEntity<>(statusOutput, HttpStatus.CREATED);
        } catch (Exception exception) {
            statusOutput.getErrorList().add("username already exists");
            statusOutput.setSucceded(false);
            return new ResponseEntity<>(statusOutput, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/profiles/update")
    public ResponseEntity<Object> updateProfile(@Validated @RequestBody ProfileInputDto profileInputDto, BindingResult bindingResult) {
        StatusOutput statusOutput = new StatusOutput();
        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                statusOutput.getErrorList().add(fe.getDefaultMessage());
            }
            statusOutput.setSucceded(false);
            return new ResponseEntity<>(statusOutput, HttpStatus.BAD_REQUEST);
        }
        try {
            profileService.updateProfile(profileInputDto);
            statusOutput.setSuccededMessage("profile updated");
            statusOutput.setSucceded(true);
            return new ResponseEntity<>(statusOutput, HttpStatus.OK);
        } catch (Exception exception) {
            statusOutput.getErrorList().add("username already exists");
            statusOutput.setSucceded(false);
            return new ResponseEntity<>(statusOutput, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/profiles")
    public ResponseEntity<Object> getProfile() {
        StatusOutput statusOutput = new StatusOutput();
        try {
            ProfileOutputDto profileOutputDto = profileService.getProfile();
            profileOutputDto.setSucceded(true);
            return new ResponseEntity<>(profileOutputDto, HttpStatus.OK);
        } catch (Exception exception) {
            statusOutput.getErrorList().add("profile not found");
            statusOutput.getErrorList().add(exception.getMessage());
            statusOutput.setSucceded(false);
            return new ResponseEntity<>(statusOutput, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/profiles")
    public ResponseEntity<Object> deleteProfile() {
        StatusOutput statusOutput = new StatusOutput();
        try {
            profileService.deleteProfile();
            statusOutput.setSuccededMessage("Profile deleted");
            statusOutput.setSucceded(true);
            return new ResponseEntity<>(statusOutput, HttpStatus.OK);
        } catch (Exception exception) {
            statusOutput.getErrorList().add("could not find profile");
            statusOutput.getErrorList().add(exception.getMessage());
            statusOutput.setSucceded(false);
            return new ResponseEntity<>(statusOutput, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/profiles/guitars")
    public ResponseEntity<Object> addGuitar(@RequestBody GuitarInputDto guitarInputDto) {
        StatusOutput statusOutput = new StatusOutput();
        try {
            profileService.addGuitarToProfile(guitarInputDto);
            statusOutput.setSuccededMessage("Guitar added");
            statusOutput.setSucceded(true);
            return new ResponseEntity<>(statusOutput, HttpStatus.OK);
        } catch (Exception exception) {
            statusOutput.getErrorList().add("You already liked the guitar.");
            statusOutput.getErrorList().add(exception.getMessage());
            statusOutput.setSucceded(false);
            return new ResponseEntity<>(statusOutput, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/profiles/remove/guitars/from-list")
    public ResponseEntity<Object> removeGuitarFromList(@RequestBody GuitarInputDto guitarInputDto) {
        StatusOutput statusOutput = new StatusOutput();
        try {
            profileService.removeGuitarFromList(guitarInputDto);
            statusOutput.setSuccededMessage("Guitar removed from your list");
            statusOutput.setSucceded(true);
            return new ResponseEntity<>(statusOutput, HttpStatus.OK);
        } catch (Exception exception) {
            statusOutput.getErrorList().add("Guitar not found");
            statusOutput.getErrorList().add(exception.getMessage());
            statusOutput.setSucceded(false);
            return new ResponseEntity<>(statusOutput, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/profiles/guitars/{id}/reviews")
    public ResponseEntity<Object> writeReview(@PathVariable Long id, @RequestBody ReviewInputDto reviewInputDto) {
        StatusOutput statusOutput = new StatusOutput();
        try {
            if(reviewInputDto.title.isEmpty() || reviewInputDto.details.isEmpty()) {
                throw new RuntimeException();
            }
            profileService.writeReview(id, reviewInputDto);
            statusOutput.setSuccededMessage("Review added");
            statusOutput.setSucceded(true);
            return new ResponseEntity<>(statusOutput, HttpStatus.OK);
        } catch (Exception exception) {
            statusOutput.setSuccededMessage("Something went wrong, Could not add review");
            statusOutput.setSucceded(false);
            return new ResponseEntity<>(statusOutput, HttpStatus.BAD_REQUEST);
        }
    }
}
