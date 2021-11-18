package com.betulsahin.auctionshortenedurl.services;

import com.betulsahin.auctionshortenedurl.dtos.*;
import com.betulsahin.auctionshortenedurl.models.User;
import com.betulsahin.auctionshortenedurl.models.UserUrl;
import com.betulsahin.auctionshortenedurl.repositories.UserRepository;
import com.betulsahin.auctionshortenedurl.repositories.UserUrlRepository;
import com.betulsahin.auctionshortenedurl.services.abstractions.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserUrlRepository userUrlRepository;

    @Transactional
    @Override
    public Optional<User> signup(RegisterRequest request) {
        Optional<User> userOptional = userRepository
                .findByUsername(request.getUsername());

        if(userOptional.isPresent()){
            throw new IllegalStateException("Hata");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(request.getPassword());
        User savedUser = userRepository.save(newUser);

        return Optional.of(savedUser);
    }

    @Transactional
    @Override
    public UserUrlCreateResponse create(UserUrlCreateRequest request, Long userId) {

        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent()){
            throw new IllegalStateException("Hata");
        }

        String shortenedUrl = UUID.randomUUID().toString();
        UserUrl userUrl = new UserUrl();
        userUrl.setUser(user.get());
        userUrl.setOriginalUrl(request.getOriginalUrl());
        userUrl.setShortendUrl(shortenedUrl);

        return new UserUrlCreateResponse(user.get().getId(), shortenedUrl);
    }

    @Transactional(readOnly = true)
    @Override
    public String getByOriginalUrl(String originalUrl) {
        String shortenedUrl = userUrlRepository
                .findByOriginalUrl(originalUrl)
                .get()
                .getShortendUrl();

        return shortenedUrl;
    }

    @Transactional(readOnly = true)
    @Override
    public String getByShortenedUrl(String shortenedUrl) {
        String originalUrl = userUrlRepository
                .findByShortendUrl(shortenedUrl)
                .get()
                .getOriginalUrl();

        return originalUrl;
    }
}
