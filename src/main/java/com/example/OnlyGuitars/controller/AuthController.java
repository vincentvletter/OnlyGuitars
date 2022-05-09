package com.example.OnlyGuitars.controller;

import com.example.OnlyGuitars.dto.AuthDto;
import com.example.OnlyGuitars.dto.StatusOutput;
import com.example.OnlyGuitars.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    JwtService jwtService;


    @PostMapping("/login")
    public ResponseEntity<Object> signIn(@RequestBody AuthDto authDto) {
        StatusOutput statusOutput = new StatusOutput();
        try {
            UsernamePasswordAuthenticationToken up =
                    new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword());
            Authentication auth = authManager.authenticate(up);

            UserDetails ud = (UserDetails) auth.getPrincipal();
            String token = jwtService.generateToken(ud);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body(token);
        } catch (Exception exception) {
            statusOutput.getErrorList().add("wrong username or password");
            statusOutput.getErrorList().add(exception.getMessage());
            statusOutput.setSucceded(false);
            return new ResponseEntity<>(statusOutput, HttpStatus.BAD_REQUEST);
        }
    }
}
