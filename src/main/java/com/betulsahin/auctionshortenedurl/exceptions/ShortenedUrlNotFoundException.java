package com.betulsahin.auctionshortenedurl.exceptions;

public class ShortenedUrlNotFoundException extends RuntimeException {
    public ShortenedUrlNotFoundException(String message) {
        super(message);
    }
}
