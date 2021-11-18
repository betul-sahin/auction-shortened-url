package com.betulsahin.auctionshortenedurl.services;

import com.betulsahin.auctionshortenedurl.dtos.*;
import com.betulsahin.auctionshortenedurl.mappers.UserUrlMapper;
import com.betulsahin.auctionshortenedurl.models.AppUser;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserUrlRepository userUrlRepository;
    private final UserUrlMapper userUrlMapper;

    @Transactional
    @Override
    public Optional<AppUser> signup(RegisterRequest request) {
        Optional<AppUser> userOptional = userRepository
                .findByUsername(request.getUsername());

        if(userOptional.isPresent()){
            throw new IllegalStateException("Hata");
        }

        AppUser newAppUser = new AppUser();
        newAppUser.setUsername(request.getUsername());
        newAppUser.setPassword(request.getPassword());
        AppUser savedAppUser = userRepository.save(newAppUser);

        return Optional.of(savedAppUser);
    }

    @Transactional
    @Override
    public UserUrlCreateResponse create(UserUrlCreateRequest request, Long userId) {

        Optional<AppUser> user = userRepository.findById(userId);

        if(user.isPresent()){
            throw new IllegalStateException("Hata");
        }

        String shortenedUrl = UUID.randomUUID().toString();
        UserUrl userUrl = new UserUrl();
        userUrl.setAppUser(user.get());
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

    @Transactional(readOnly = true)
    @Override
    public List<UserUrlDto> getAllByUserId(Long userId) {
        return userUrlRepository.findAllByUserId(userId)
                .stream()
                .map(userUrlMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public UserUrlDto getShortenedUrlByUserIdAndUrlId(Long userId, Long urlId) {
        UserUrl userUrl = userUrlRepository.findByByUserIdAndUrlId(userId, urlId)
                .orElseThrow(() -> new RuntimeException("hata"));

        return userUrlMapper.mapToDto(userUrl);
    }

    @Transactional
    @Override
    public void deleteShortenedUrlByUserIdAndUrlId(Long userId, Long urlId) {
        Optional<UserUrl> userUrlOptional = userUrlRepository.findByByUserIdAndUrlId(userId, urlId);
        if(!userUrlOptional.isPresent()){
            throw new RuntimeException("hata");
        }

        userUrlRepository.delete(userUrlOptional.get());
    }
}
