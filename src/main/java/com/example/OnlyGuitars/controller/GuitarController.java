package com.example.OnlyGuitars.controller;

import com.example.OnlyGuitars.dto.GuitarOutputDto;
import com.example.OnlyGuitars.dto.StatusOutput;
import com.example.OnlyGuitars.service.GuitarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
public class GuitarController {

    @Autowired
    GuitarService guitarService;


    @PostMapping("/guitars/create")
    public ResponseEntity<Object> createGuitar(@Validated @RequestBody MultipartFile image, String brand, String model) {
        StatusOutput statusOutput = new StatusOutput();

        guitarService.createGuitar(image, brand, model);
        statusOutput.setSuccededMessage("Guitar added");
        statusOutput.setSucceded(true);
        return new ResponseEntity<>(statusOutput, HttpStatus.CREATED);
    }

    @GetMapping("/guitars/{id}")
    public ResponseEntity<Object> getGuitar(@PathVariable Long id) {
        StatusOutput statusOutput = new StatusOutput();
        GuitarOutputDto guitarOutputDto = guitarService.getGuitar(id);
        statusOutput.setSucceded(true);
        return new ResponseEntity<>(guitarOutputDto, HttpStatus.OK);
    }

    @GetMapping("/guitars")
    public ResponseEntity<Object> getAllGuitars() {
        StatusOutput statusOutput = new StatusOutput();
        List<GuitarOutputDto> allGuitars = guitarService.getAllGuitars();
        statusOutput.setSucceded(true);
        return new ResponseEntity<>(allGuitars, HttpStatus.OK);
    }

    @DeleteMapping("guitars/{id}/delete")
    public ResponseEntity<Object> deleteGuitar(@PathVariable Long id) {
        StatusOutput statusOutput = new StatusOutput();
        guitarService.deleteGuitar(id);
        statusOutput.setSuccededMessage("Guitar deleted");
        statusOutput.setSucceded(true);
        return new ResponseEntity<>(statusOutput, HttpStatus.OK);
    }

    @GetMapping(value = "/guitars/{id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getGuitarImage(@PathVariable Long id) {
        byte[] image = guitarService.getImage(id);
        return image;
    }

    @GetMapping(value = "/image/{id}/download", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public HttpEntity<byte[]> getArticleImage(@PathVariable Long id) {
        byte[] image = guitarService.getImage(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(image.length);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"guitar-picture.jpg\"");
        return new HttpEntity<byte[]>(image, headers);
    }
}
