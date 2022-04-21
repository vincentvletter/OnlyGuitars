package com.example.OnlyGuitars.controller;

import com.example.OnlyGuitars.dto.RequestInputDto;
import com.example.OnlyGuitars.dto.RequestOutputDto;
import com.example.OnlyGuitars.dto.StatusOutput;
import com.example.OnlyGuitars.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RequestController {

    @Autowired
    RequestService requestService;

    @PostMapping("/requests")
    public ResponseEntity<Object> createRequest(@Validated @RequestBody RequestInputDto requestInputDto, BindingResult bindingResult) {
        StatusOutput statusOutput = new StatusOutput();
        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                statusOutput.getErrorList().add(fe.getDefaultMessage());
            }
            statusOutput.setSucceded(false);
            return new ResponseEntity<>(statusOutput, HttpStatus.BAD_REQUEST);
        }
        try {
            statusOutput.setSuccededMessage("Request send");
            statusOutput.setSucceded(true);
            requestService.createRequest(requestInputDto);
            return new ResponseEntity<>(statusOutput, HttpStatus.CREATED);
        } catch (Exception exception) {
            statusOutput.getErrorList().add("something went wrong");
            statusOutput.setSucceded(false);
            return new ResponseEntity<>(statusOutput, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/requests")
    public ResponseEntity<Object> getAllRequests() {
        StatusOutput statusOutput = new StatusOutput();
        try {
            statusOutput.setSucceded(true);
            List<RequestOutputDto> allRequests = requestService.getAllRequests();
            return new ResponseEntity<>(allRequests, HttpStatus.OK);
        } catch (Exception exception) {
            statusOutput.getErrorList().add("something went wrong");
            statusOutput.setSucceded(false);
            return new ResponseEntity<>(statusOutput, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/requests/{id}")
    public ResponseEntity<Object> deleteRequest(@PathVariable Long id) {
        StatusOutput statusOutput = new StatusOutput();
        try {
            statusOutput.setSuccededMessage("Request deleted");
            statusOutput.setSucceded(true);
            requestService.deleteRequest(id);
            return new ResponseEntity<>(statusOutput, HttpStatus.OK);
        } catch (Exception exception) {
            statusOutput.getErrorList().add("something went wrong");
            statusOutput.setSucceded(false);
            return new ResponseEntity<>(statusOutput, HttpStatus.BAD_REQUEST);
        }
    }
}
