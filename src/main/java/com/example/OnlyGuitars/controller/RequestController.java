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
            requestService.createRequest(requestInputDto);
            statusOutput.setSuccededMessage("Request send");
            statusOutput.setSucceded(true);
            return new ResponseEntity<>(statusOutput, HttpStatus.CREATED);
        } catch (Exception exception) {
            statusOutput.getErrorList().add("something went wrong");
            statusOutput.setSucceded(false);
            return new ResponseEntity<>(statusOutput, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/requests/get")
    public ResponseEntity<Object> getAllRequests() {
        StatusOutput statusOutput = new StatusOutput();
        try {
            List<RequestOutputDto> allRequests = requestService.getAllRequests();
            statusOutput.setSucceded(true);
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
            requestService.deleteRequest(id);
            statusOutput.setSuccededMessage("Request deleted");
            statusOutput.setSucceded(true);
            return new ResponseEntity<>(statusOutput, HttpStatus.OK);
        } catch (Exception exception) {
            statusOutput.getErrorList().add(exception.getMessage());
            statusOutput.setSucceded(false);
            return new ResponseEntity<>(statusOutput, HttpStatus.BAD_REQUEST);
        }
    }
}
