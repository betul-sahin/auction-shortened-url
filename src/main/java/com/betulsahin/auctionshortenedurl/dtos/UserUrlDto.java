package com.betulsahin.auctionshortenedurl.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUrlDto {
    private Long userId;
    private String shortenedUrl;
    private String originalUrl;
}
