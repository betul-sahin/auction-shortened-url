package com.betulsahin.auctionshortenedurl.services.abstractions;

import com.betulsahin.auctionshortenedurl.dtos.*;
import com.betulsahin.auctionshortenedurl.models.AppUser;
import org.springframework.http.HttpHeaders;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<AppUser> signup(RegisterRequest request);
    UserUrlCreateResponse create(UserUrlCreateRequest request, Long userId);
    HttpHeaders getByShortenedUrl(String shortenedUrl) throws URISyntaxException;
    List<UserUrlDto> getAllByUserId(Long userId);
    UserUrlDto getShortenedUrlByUserIdAndUrlId(Long userId, Long urlId);
    void deleteShortenedUrlByUserIdAndUrlId(Long userId, Long urlId);
}
