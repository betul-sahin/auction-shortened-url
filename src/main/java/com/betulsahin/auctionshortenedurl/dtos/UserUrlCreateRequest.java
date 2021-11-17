package com.betulsahin.auctionshortenedurl.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUrlCreateRequest {
    private String originalUrl;
}
