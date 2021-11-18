package com.betulsahin.auctionshortenedurl.mappers;

import com.betulsahin.auctionshortenedurl.dtos.UserUrlDto;
import com.betulsahin.auctionshortenedurl.models.UserUrl;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserUrlMapper {
    UserUrlDto mapToDto(UserUrl userUrl);
}
