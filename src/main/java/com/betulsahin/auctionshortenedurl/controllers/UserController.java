package com.betulsahin.auctionshortenedurl.controllers;

import com.betulsahin.auctionshortenedurl.dtos.RegisterRequest;
import com.betulsahin.auctionshortenedurl.dtos.RegisterResponse;
import com.betulsahin.auctionshortenedurl.dtos.UserUrlCreateRequest;
import com.betulsahin.auctionshortenedurl.dtos.UserUrlCreateResponse;
import com.betulsahin.auctionshortenedurl.models.User;
import com.betulsahin.auctionshortenedurl.models.UserUrl;
import com.betulsahin.auctionshortenedurl.services.abstractions.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<RegisterResponse> signup(@Valid @RequestBody RegisterRequest request){

        Optional<User> userOptional = userService.signup(request);
        if(!userOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
                new RegisterResponse(userOptional.get().getId()),
                HttpStatus.CREATED);
    }

    @PostMapping("/{userId}/url/create")
    public ResponseEntity<UserUrlCreateResponse> create(@Valid @RequestBody UserUrlCreateRequest request,
                                                        @PathVariable Long userId){

        return new ResponseEntity<>(
                userService.create(request, userId),
                HttpStatus.CREATED);
    }
}
