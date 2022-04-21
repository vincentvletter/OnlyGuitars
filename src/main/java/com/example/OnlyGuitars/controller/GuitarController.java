package com.example.OnlyGuitars.controller;


import com.example.OnlyGuitars.dto.GuitarInputDto;
import com.example.OnlyGuitars.dto.GuitarOutputDto;
import com.example.OnlyGuitars.dto.StatusOutput;
import com.example.OnlyGuitars.model.Guitar;
import com.example.OnlyGuitars.repository.GuitarRepository;
import com.example.OnlyGuitars.service.GuitarService;
import com.example.OnlyGuitars.service.GuitarServiceImpl;
import com.example.OnlyGuitars.service.ReviewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class GuitarController {

    @Autowired
    GuitarService guitarService;


    @PostMapping(value = "/guitars")
    public ResponseEntity<Object> createGuitar(@Validated @RequestBody MultipartFile image, String brand, String model) {
        StatusOutput statusOutput = new StatusOutput();
        try {
            guitarService.createGuitar(image, brand, model);
            statusOutput.setSuccededMessage("Guitar added");
            statusOutput.setSucceded(true);
            return new ResponseEntity<>(statusOutput, HttpStatus.CREATED);
        } catch (Exception exception) {
            statusOutput.getErrorList().add("username already exists");
            statusOutput.setSucceded(false);
            return new ResponseEntity<>(statusOutput, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/guitars/{id}")
    public ResponseEntity<Object> getGuitar(@PathVariable Long id) {
        StatusOutput statusOutput = new StatusOutput();
        try {
            statusOutput.setSucceded(true);
            GuitarOutputDto guitarOutputDto = guitarService.getGuitar(id);
            return new ResponseEntity<>(guitarOutputDto, HttpStatus.OK);
        } catch (Exception exception) {
            statusOutput.getErrorList().add("something went wrong");
            statusOutput.getErrorList().add(exception.getMessage());
            statusOutput.setSucceded(false);
            return new ResponseEntity<>(statusOutput, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/guitars")
    public ResponseEntity<Object> getAllGuitars() {
        StatusOutput statusOutput = new StatusOutput();
        try {
            List<GuitarOutputDto> allGuitars = guitarService.getAllGuitars();
            statusOutput.setSucceded(true);
            return new ResponseEntity<>(allGuitars, HttpStatus.OK);
        } catch (Exception exception) {
            statusOutput.getErrorList().add("something went wrong");
            statusOutput.getErrorList().add(exception.getMessage());
            statusOutput.setSucceded(false);
            return new ResponseEntity<>(statusOutput, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("guitars/{id}")
    public ResponseEntity<Object> deleteGuitar(@PathVariable Long id) {
        StatusOutput statusOutput = new StatusOutput();
        try {
            guitarService.deleteGuitar(id);
            statusOutput.setSuccededMessage("Guitar deleted");
            statusOutput.setSucceded(true);
            return new ResponseEntity<>(statusOutput, HttpStatus.OK);
        } catch (Exception exception) {
            statusOutput.getErrorList().add("something went wrong");
            statusOutput.getErrorList().add(exception.getMessage());
            statusOutput.setSucceded(false);
            return new ResponseEntity<>(statusOutput, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/guitars/{id}/image", produces = MediaType.IMAGE_GIF_VALUE)
    public @ResponseBody byte[] getGuitarImage(@PathVariable Long id) {
        byte[] image = guitarService.getImage(id);
        return image;
    }
}
