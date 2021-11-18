package com.betulsahin.auctionshortenedurl.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUrlCreateRequest {

    @ApiModelProperty(example = "https://localhost:8080/l/uygulamaya-ozel-kampanyali-tapular")
    private String originalUrl;
}
