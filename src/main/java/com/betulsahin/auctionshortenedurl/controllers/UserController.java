package com.betulsahin.auctionshortenedurl.controllers;

import com.betulsahin.auctionshortenedurl.dtos.RegisterRequest;
import com.betulsahin.auctionshortenedurl.dtos.RegisterResponse;
import com.betulsahin.auctionshortenedurl.models.User;
import com.betulsahin.auctionshortenedurl.services.abstractions.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<RegisterResponse> signup(RegisterRequest request){

        Optional<User> userOptional = userService.signup(request);

        if(!userOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
                new RegisterResponse(userOptional.get().getId()),
                HttpStatus.CREATED);
    }
}
