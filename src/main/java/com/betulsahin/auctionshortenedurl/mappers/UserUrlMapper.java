package com.betulsahin.auctionshortenedurl.mappers;

import com.betulsahin.auctionshortenedurl.dtos.UserUrlDto;
import com.betulsahin.auctionshortenedurl.models.UserUrl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserUrlMapper {

    @Mapping(target="userId", source="appUser.id")
    UserUrlDto mapToDto(UserUrl userUrl);
}
