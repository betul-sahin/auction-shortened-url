package com.betulsahin.auctionshortenedurl.repositories;

import com.betulsahin.auctionshortenedurl.models.UserUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserUrlRepository extends JpaRepository<UserUrl, Long> {
    Optional<UserUrl> findByOriginalUrl(String originalUrl);
    Optional<UserUrl> findByShortendUrl(String shortendUrl);

    @Query("SELECT uu FROM UserUrl uu WHERE uu.user.id = ?1 AND uu.id = ?2")
    Optional<UserUrl> findByByUserIdAndUrlId(Long userId, Long urlId);

    @Query("SELECT uu FROM UserUrl uu WHERE uu.user.id = ?1")
    List<UserUrl> findAllByUserId(Long userId);
}