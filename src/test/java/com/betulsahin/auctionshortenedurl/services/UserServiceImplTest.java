package com.betulsahin.auctionshortenedurl.services;

import com.betulsahin.auctionshortenedurl.dtos.RegisterRequest;
import com.betulsahin.auctionshortenedurl.mappers.UserMapper;
import com.betulsahin.auctionshortenedurl.mappers.UserUrlMapper;
import com.betulsahin.auctionshortenedurl.models.AppUser;
import com.betulsahin.auctionshortenedurl.repositories.UserRepository;
import com.betulsahin.auctionshortenedurl.repositories.UserUrlRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
                () -> assertEquals(expected, actual),
                () -> assertEquals(expected.getUsername(), actual.get().getUsername())
        );
    }
}