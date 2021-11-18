package com.betulsahin.auctionshortenedurl.exceptions;

public class UserIsAlreadyExistException extends RuntimeException {
    public UserIsAlreadyExistException(String message) {
        super(message);
    }
}
