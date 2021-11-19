package com.betulsahin.auctionshortenedurl.controllers;

import com.betulsahin.auctionshortenedurl.dtos.*;
import com.betulsahin.auctionshortenedurl.exceptions.UserUrlNotFoundException;
import com.betulsahin.auctionshortenedurl.models.AppUser;
import com.betulsahin.auctionshortenedurl.services.abstractions.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserService mockUserService;

    @InjectMocks
    UserController underTest;

    @Test
    void signup_itShouldRegisterSuccessfully(){
        // given
        AppUser newUser = new AppUser();
        RegisterRequest request = new RegisterRequest();

        when(mockUserService.signup(any()))
                .thenReturn(Optional.of(newUser));

        // when
        ResponseEntity<RegisterResponse> response = underTest.signup(request);
        RegisterResponse actual = response.getBody();

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode())
        );
    }

    @Test
    void signup_itShouldReturnStatusBadRequest(){
        // given
        RegisterRequest request = new RegisterRequest();

        when(mockUserService.signup(any()))
                .thenReturn(Optional.empty());

        // when
        ResponseEntity<RegisterResponse> response = underTest.signup(request);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void create_itShouldCreateSuccessfully(){
        // given
        UserUrlCreateRequest request = new UserUrlCreateRequest();
        UserUrlCreateResponse createResponse = new UserUrlCreateResponse();
        Long userId = 1L;

        when(mockUserService.create(any(), any()))
                .thenReturn(createResponse);

        // when
        ResponseEntity<UserUrlCreateResponse> response = underTest.create(request, userId);
        UserUrlCreateResponse actual = response.getBody();

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode())
        );
    }

    @Test
    void getByShortenedUrl_itShouldReturnHttpStatusOK() throws URISyntaxException {
        // given
        URI uri = new URI("https://www.tapu.com/l/uygulamaya-ozel-kampanyali-tapular");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);

        when(mockUserService.getByShortenedUrl(any())).thenReturn(httpHeaders);

        // when
        ResponseEntity<?> response = underTest.getByShortenedUrl("http://localhost:8080/s/XXXyyyZZZZ");

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    void getAllByUserId_itShouldReturnUserUrlDtoList(){
        // given
        UserUrlDto userUrlDto = new UserUrlDto();
        List<UserUrlDto> expected = Arrays.asList(userUrlDto);

        when(mockUserService.getAllByUserId(anyLong())).thenReturn(expected);

        // when
        ResponseEntity<List<UserUrlDto>> response = underTest.getAllByUserId(1L);
        List<UserUrlDto> actual = response.getBody();

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(expected.size(), actual.size())
        );
    }

    @Test
    void getShortenedUrlByUserIdAndUrlId_itShouldReturnUserUrlDto(){
        // given
        UserUrlDto expected = new UserUrlDto();
        expected.setUserId(1L);

        when(mockUserService.getShortenedUrlByUserIdAndUrlId(anyLong(), anyLong()))
                .thenReturn(expected);

        // when
        ResponseEntity<UserUrlDto> response = underTest.getShortenedUrlByUserIdAndUrlId(1L, 1L);
        UserUrlDto actual = response.getBody();

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(expected.getUserId(), actual.getUserId())
        );
    }

    @Test
    void deleteById_itShouldDeleteSuccesfully(){
        // given
        // when
        ResponseEntity<Void> response = underTest.deleteById(1L, 1L);

        // then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteById_itShouldReturnStatusNotFound_whenUserUrlIdNotExist(){
        // given
        doThrow(UserUrlNotFoundException.class).
                when(mockUserService).deleteShortenedUrlByUserIdAndUrlId(anyLong(), anyLong());

        // when
        ResponseEntity<Void> response = underTest.deleteById(1L, 1L);

        // then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}