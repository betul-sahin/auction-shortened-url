package com.betulsahin.auctionshortenedurl.exceptions;

public class UserUrlNotFoundException extends RuntimeException{
    public UserUrlNotFoundException(String message) {
        super(message);
    }
}
