package com.example.OnlyGuitars.controller;

import com.example.OnlyGuitars.dto.*;
import com.example.OnlyGuitars.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        profileService.createProfile(profileInputDto);

        statusOutput.setSuccededMessage("profiel aangemaakt");
        statusOutput.setSucceded(true);

        return new ResponseEntity<>(statusOutput, HttpStatus.CREATED);
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        profileService.updateProfile(profileInputDto, userDetails.getUsername());

        statusOutput.setSuccededMessage("profile updated");
        statusOutput.setSucceded(true);

        return new ResponseEntity<>(statusOutput, HttpStatus.OK);
    }

    @GetMapping("/profiles")
    public ResponseEntity<Object> getProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        ProfileOutputDto profileOutputDto = profileService.getProfile(userDetails.getUsername());

        profileOutputDto.setSucceded(true);

        return new ResponseEntity<>(profileOutputDto, HttpStatus.OK);
    }

    @DeleteMapping("/profiles")
    public ResponseEntity<Object> deleteProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        profileService.deleteProfile(userDetails.getUsername());

        StatusOutput statusOutput = new StatusOutput();
        statusOutput.setSuccededMessage("Profile deleted");
        statusOutput.setSucceded(true);

        return new ResponseEntity<>(statusOutput, HttpStatus.OK);
    }

    @PostMapping("/profiles/guitars")
    public ResponseEntity<Object> addGuitar(@RequestBody GuitarInputDto guitarInputDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        profileService.addGuitarToProfile(guitarInputDto, userDetails.getUsername());

        StatusOutput statusOutput = new StatusOutput();
        statusOutput.setSuccededMessage("Guitar added");
        statusOutput.setSucceded(true);

        return new ResponseEntity<>(statusOutput, HttpStatus.OK);
    }

    @PostMapping("/profiles/remove/guitars/from-list")
    public ResponseEntity<Object> removeGuitarFromList(@RequestBody GuitarInputDto guitarInputDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        profileService.removeGuitarFromList(guitarInputDto, userDetails.getUsername());

        StatusOutput statusOutput = new StatusOutput();
        statusOutput.setSuccededMessage("Guitar removed from your list");
        statusOutput.setSucceded(true);

        return new ResponseEntity<>(statusOutput, HttpStatus.OK);
    }

    @PostMapping("/profiles/guitars/{id}/reviews")
    public ResponseEntity<Object> writeReview(@Validated @PathVariable Long id, @RequestBody ReviewInputDto reviewInputDto, BindingResult bindingResult) {
        StatusOutput statusOutput = new StatusOutput();
        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                statusOutput.getErrorList().add(fe.getDefaultMessage());
            }
            statusOutput.setSucceded(false);
            return new ResponseEntity<>(statusOutput, HttpStatus.BAD_REQUEST);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        profileService.writeReview(id, reviewInputDto, userDetails.getUsername());

        statusOutput.setSuccededMessage("Review added");
        statusOutput.setSucceded(true);

        return new ResponseEntity<>(statusOutput, HttpStatus.OK);
    }
}
