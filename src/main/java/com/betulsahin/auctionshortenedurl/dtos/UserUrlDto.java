package com.betulsahin.auctionshortenedurl.dtos;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(example = "http://localhost:8080/s/XXXyyyZZZZ ")
    private String shortenedUrl;

    @ApiModelProperty(example = "https://localhost:8080/l/uygulamaya-ozel-kampanyali-tapular")
    private String originalUrl;
}
