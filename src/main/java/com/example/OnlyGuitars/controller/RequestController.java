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
        requestService.createRequest(requestInputDto);

        statusOutput.setSuccededMessage("Request send");
        statusOutput.setSucceded(true);

        return new ResponseEntity<>(statusOutput, HttpStatus.CREATED);
    }

    @GetMapping("/requests/get")
    public ResponseEntity<Object> getAllRequests() {
        List<RequestOutputDto> allRequests = requestService.getAllRequests();

        StatusOutput statusOutput = new StatusOutput();
        statusOutput.setSucceded(true);

        return new ResponseEntity<>(allRequests, HttpStatus.OK);
    }

    @DeleteMapping("/requests/{id}")
    public ResponseEntity<Object> deleteRequest(@PathVariable Long id) {
        requestService.deleteRequest(id);

        StatusOutput statusOutput = new StatusOutput();
        statusOutput.setSuccededMessage("Request deleted");
        statusOutput.setSucceded(true);

        return new ResponseEntity<>(statusOutput, HttpStatus.OK);
    }
}
