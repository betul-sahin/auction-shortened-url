package com.betulsahin.auctionshortenedurl.controllers;

import com.betulsahin.auctionshortenedurl.dtos.*;
import com.betulsahin.auctionshortenedurl.models.User;
import com.betulsahin.auctionshortenedurl.models.UserUrl;
import com.betulsahin.auctionshortenedurl.services.abstractions.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users")
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

    @GetMapping("/l/{originalUrl}")
    public ResponseEntity<String> getByOriginalUrl(@PathVariable String originalUrl){

        return new ResponseEntity<>(
                userService.getByOriginalUrl(originalUrl),
                HttpStatus.OK);
    }

    @GetMapping("/s/{shortenedUrl}")
    public ResponseEntity<String> getByShortenedUrl(@PathVariable String shortenedUrl){

        return new ResponseEntity<>(
                userService.getByShortenedUrl(shortenedUrl),
                HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserUrlDto>> getAllByUserId(@PathVariable Long userId){

        return new ResponseEntity<>(
                userService.getAllByUserId(userId),
                HttpStatus.OK);
    }

    @GetMapping("/{userId}/url/detail/{urlId}")
    public ResponseEntity<UserUrlDto> getShortenedUrlByUserIdAndUrlId(@PathVariable Long userId,
                                                                      @PathVariable Long urlId){

        return new ResponseEntity<>(
                userService.getShortenedUrlByUserIdAndUrlId(userId, urlId),
                HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/url/delete/{urlId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long userId,
                                           @PathVariable Long urlId){
        try{
            userService.deleteShortenedUrlByUserIdAndUrlId(userId, urlId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
