package com.betulsahin.auctionshortenedurl.repositories;

import com.betulsahin.auctionshortenedurl.models.UserUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserUrlRepository extends JpaRepository<UserUrl, Long> {
}
