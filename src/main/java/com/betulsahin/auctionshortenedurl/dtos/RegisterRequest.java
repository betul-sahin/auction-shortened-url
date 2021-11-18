package com.betulsahin.auctionshortenedurl.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotEmpty
    @Size(max=20, min=3, message = "Your username must be between 3 and 20 characters.")
    private String username;

    @NotEmpty
    @Size(max=8, min=8, message = "Your password must be between 8 characters.")
    private String password;
}
