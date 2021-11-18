package com.betulsahin.auctionshortenedurl.models;

import com.betulsahin.auctionshortenedurl.models.abstractions.AbstractBaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class AppUser extends AbstractBaseEntity {
    private String username;
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "appUser", fetch = FetchType.LAZY)
    private Set<UserUrl> urls = new HashSet<>();
}
