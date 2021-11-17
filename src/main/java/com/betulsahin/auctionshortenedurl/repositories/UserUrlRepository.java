package com.betulsahin.auctionshortenedurl.repositories;

import com.betulsahin.auctionshortenedurl.models.UserUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserUrlRepository extends JpaRepository<UserUrl, Long> {
    Optional<UserUrl> findByOriginalUrl(String originalUrl);
    Optional<UserUrl> findByShortendUrl(String shortendUrl);
}
