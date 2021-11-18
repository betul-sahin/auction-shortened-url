package com.betulsahin.auctionshortenedurl.services.abstractions;

import com.betulsahin.auctionshortenedurl.dtos.*;
import com.betulsahin.auctionshortenedurl.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> signup(RegisterRequest request);
    UserUrlCreateResponse create(UserUrlCreateRequest request, Long userId);
    String getByOriginalUrl(String originalUrl);
    String getByShortenedUrl(String shortenedUrl);
}
