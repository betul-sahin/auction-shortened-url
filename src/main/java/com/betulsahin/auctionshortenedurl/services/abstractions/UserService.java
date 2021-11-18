package com.betulsahin.auctionshortenedurl.services.abstractions;

import com.betulsahin.auctionshortenedurl.dtos.*;
import com.betulsahin.auctionshortenedurl.models.AppUser;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<AppUser> signup(RegisterRequest request);
    UserUrlCreateResponse create(UserUrlCreateRequest request, Long userId);
    String getByOriginalUrl(String originalUrl);
    String getByShortenedUrl(String shortenedUrl);
    List<UserUrlDto> getAllByUserId(Long userId);
    UserUrlDto getShortenedUrlByUserIdAndUrlId(Long userId, Long urlId);
    void deleteShortenedUrlByUserIdAndUrlId(Long userId, Long urlId);
}
