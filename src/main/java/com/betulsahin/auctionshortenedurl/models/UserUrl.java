package com.betulsahin.auctionshortenedurl.models;

import com.betulsahin.auctionshortenedurl.models.abstractions.AbstractBaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@Entity
public class UserUrl extends AbstractBaseEntity {
    private String shortendUrl;
    private String originalUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser appUser;
}
