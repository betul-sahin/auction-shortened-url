package com.betulsahin.auctionshortenedurl.services;

import com.betulsahin.auctionshortenedurl.dtos.*;
import com.betulsahin.auctionshortenedurl.exceptions.ShortenedUrlNotFoundException;
import com.betulsahin.auctionshortenedurl.exceptions.UserIsAlreadyExistException;
import com.betulsahin.auctionshortenedurl.exceptions.UserNotFoundException;
import com.betulsahin.auctionshortenedurl.exceptions.UserUrlNotFoundException;
import com.betulsahin.auctionshortenedurl.mappers.UserMapper;
import com.betulsahin.auctionshortenedurl.mappers.UserUrlMapper;
import com.betulsahin.auctionshortenedurl.models.AppUser;
import com.betulsahin.auctionshortenedurl.models.UserUrl;
import com.betulsahin.auctionshortenedurl.repositories.UserRepository;
import com.betulsahin.auctionshortenedurl.repositories.UserUrlRepository;
import com.betulsahin.auctionshortenedurl.services.abstractions.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserUrlRepository userUrlRepository;
    private final UserUrlMapper userUrlMapper;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public Optional<AppUser> signup(RegisterRequest request) {
        Optional<AppUser> userOptional = userRepository
                .findByUsername(request.getUsername());

        if(userOptional.isPresent()){
            throw new UserIsAlreadyExistException("This user is already exist!");
        }

        AppUser newAppUser = userMapper.map(request);
        AppUser savedAppUser = userRepository.save(newAppUser);

        LOGGER.info("Registered new user {}", savedAppUser);

        return Optional.of(savedAppUser);
    }

    @Transactional
    @Override
    public UserUrlCreateResponse create(UserUrlCreateRequest request, Long userId) {

        Optional<AppUser> user = userRepository.findById(userId);

        if(!user.isPresent()){
            throw new UserNotFoundException("This user is not found!");
        }

        LOGGER.info("The user found with given userId {}", userId);

        String shortenedUrl = UUID.randomUUID().toString();

        UserUrl userUrl = new UserUrl();
        userUrl.setAppUser(user.get());
        userUrl.setOriginalUrl(request.getOriginalUrl());
        userUrl.setShortenedUrl(shortenedUrl);
        userUrlRepository.save(userUrl);

        LOGGER.info("The shortened Url saved to urls of given user {}", userUrl.toString());

        return new UserUrlCreateResponse(user.get().getId(), shortenedUrl);
    }

    @Transactional(readOnly = true)
    @Override
    public HttpHeaders getByShortenedUrl(String shortenedUrl) throws URISyntaxException {
        UserUrl originalUrl = userUrlRepository.findByShortenedUrl(shortenedUrl)
                .orElseThrow(() -> new ShortenedUrlNotFoundException("Shortened Url is not found!"));

        URI uri = new URI(originalUrl.getOriginalUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);

        LOGGER.info("Located on original url {}", originalUrl.getOriginalUrl());

        return httpHeaders;
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
                .orElseThrow(() -> new UserUrlNotFoundException("This user url is not found!"));

        return userUrlMapper.mapToDto(userUrl);
    }

    @Transactional
    @Override
    public void deleteShortenedUrlByUserIdAndUrlId(Long userId, Long urlId) {
        Optional<UserUrl> userUrlOptional = userUrlRepository.findByByUserIdAndUrlId(userId, urlId);
        if(!userUrlOptional.isPresent()){
            throw new UserUrlNotFoundException("User url not found!");
        }

        LOGGER.info("Deleted given url {}", userUrlOptional.get().getOriginalUrl());

        userUrlRepository.delete(userUrlOptional.get());
    }
}
