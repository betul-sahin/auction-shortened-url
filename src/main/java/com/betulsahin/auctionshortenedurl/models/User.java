package com.betulsahin.auctionshortenedurl.models;

import com.betulsahin.auctionshortenedurl.models.abstractions.AbstractBaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class User extends AbstractBaseEntity {
    private String username;
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserUrl> urls = new ArrayList<>();
}
