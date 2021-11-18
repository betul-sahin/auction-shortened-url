package com.betulsahin.auctionshortenedurl.mappers;

import com.betulsahin.auctionshortenedurl.dtos.RegisterRequest;
import com.betulsahin.auctionshortenedurl.models.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    AppUser map(RegisterRequest request);
}
