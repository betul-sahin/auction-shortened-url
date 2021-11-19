package com.betulsahin.auctionshortenedurl.services;

import com.betulsahin.auctionshortenedurl.dtos.RegisterRequest;
import com.betulsahin.auctionshortenedurl.dtos.UserUrlCreateRequest;
import com.betulsahin.auctionshortenedurl.dtos.UserUrlCreateResponse;
import com.betulsahin.auctionshortenedurl.dtos.UserUrlDto;
import com.betulsahin.auctionshortenedurl.exceptions.UserIsAlreadyExistException;
import com.betulsahin.auctionshortenedurl.exceptions.UserNotFoundException;
import com.betulsahin.auctionshortenedurl.exceptions.UserUrlNotFoundException;
import com.betulsahin.auctionshortenedurl.mappers.UserMapper;
import com.betulsahin.auctionshortenedurl.mappers.UserUrlMapper;
import com.betulsahin.auctionshortenedurl.models.AppUser;
import com.betulsahin.auctionshortenedurl.models.UserUrl;
import com.betulsahin.auctionshortenedurl.repositories.UserRepository;
import com.betulsahin.auctionshortenedurl.repositories.UserUrlRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    UserRepository mockUserRepository;

    @Mock
    UserUrlRepository mockUserUrlRepository;

    @Mock
    UserUrlMapper mockUserUrlMapper;

    @Mock
    UserMapper mockUserMapper;

    @InjectMocks
    UserServiceImpl underTest;

    @Test
    void signup_itShouldRegisterNewUser(){
        // given
        AppUser user = new AppUser();

        AppUser expected = new AppUser();
        expected.setUsername("Admin");

        RegisterRequest request = new RegisterRequest();

        // No user with given username
        when(mockUserRepository.findByUsername(any()))
                .thenReturn(Optional.empty());

        // Mocking signup method
        when(mockUserRepository.save(any()))
                .thenReturn(expected);

        // when
        Optional<AppUser> actual = underTest.signup(request);

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected, actual.get()),
                () -> assertEquals(expected.getUsername(), actual.get().getUsername())
        );
    }

    @Test
    void signup_itShoulThrowException_whenUserIsAlreadyExist(){
        // given
        AppUser user = new AppUser();
        RegisterRequest request = new RegisterRequest();

        when(mockUserRepository.findByUsername(any()))
                .thenReturn(Optional.of(user));

        // when
        Executable executable = () -> underTest.signup(request).get();

        // then
        assertThrows(UserIsAlreadyExistException.class, executable);
    }

    @Test
    void create_itShouldCreateUserUrl(){
        // given
        AppUser user = new AppUser();
        user.setId(1L);

        UserUrlCreateRequest request = new UserUrlCreateRequest();
        UserUrlCreateResponse response = new UserUrlCreateResponse();

        // The user with given username
        when(mockUserRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));

        // Mocking create method
        when(mockUserUrlRepository.save(any()))
                .thenReturn(new UserUrl());

        // when
        UserUrlCreateResponse actual = underTest.create(request, 1L);

        // then
        assertNotNull(actual);
    }

    @Test
    void create_itShoulThrowException_whenUserNotFoundException(){
        // given
        UserUrlCreateRequest request = new UserUrlCreateRequest();

        when(mockUserRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        // when
        Executable executable = () -> underTest.create(request, 1L);

        // then
        assertThrows(UserNotFoundException.class, executable);
    }

    @Test
    void getAllByUserId_itShouldReturnUserUrlDtoList() {
        // given
        UserUrl userUrl = new UserUrl();
        userUrl.setId(1L);
        List<UserUrl> userUrlList = Collections.singletonList(userUrl);

        UserUrlDto userUrlDto = new UserUrlDto();
        userUrlDto.setUserId(1L);

        when(mockUserUrlMapper.mapToDto(any()))
                .thenReturn(userUrlDto);

        when(mockUserUrlRepository.findAllByUserId(anyLong()))
                .thenReturn(userUrlList);

        // when
        List<UserUrlDto> actual = underTest.getAllByUserId(1L);

        // then
        assertEquals(userUrl.getId(), actual.get(0).getUserId());
    }

    @Test
    void getShortenedUrlByUserIdAndUrlId_itShouldReturnUserUrlDto(){
        // given
        UserUrl userUrl = new UserUrl();
        userUrl.setId(1L);

        UserUrlDto expected = new UserUrlDto();
        expected.setUserId(1L);

        when(mockUserUrlMapper.mapToDto(any())).thenReturn(expected);

        when(mockUserUrlRepository.findByByUserIdAndUrlId(anyLong(), anyLong()))
                .thenReturn(Optional.of(userUrl));

        // when
        UserUrlDto actual = underTest.getShortenedUrlByUserIdAndUrlId(1L, 1L);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void getShortenedUrlByUserIdAndUrlId_whenUserUrlNotFoundException(){
        // given
        when(mockUserUrlRepository.findByByUserIdAndUrlId(anyLong(), anyLong()))
                .thenReturn(Optional.empty());

        // when
        Executable executable = () -> underTest.getShortenedUrlByUserIdAndUrlId(1L, 1L);

        // then
        assertThrows(UserUrlNotFoundException.class, executable);
    }

    @Test
    void deleteShortenedUrlByUserIdAndUrlId_itShouldReturnUserUrlNotFoundException(){
        // given
        when(mockUserUrlRepository.findByByUserIdAndUrlId(anyLong(), anyLong()))
                .thenReturn(Optional.empty());

        // when
        Executable executable = () -> underTest.deleteShortenedUrlByUserIdAndUrlId(1L, 1L);

        // then
        assertThrows(UserUrlNotFoundException.class, executable);
    }
}