package com.betulsahin.auctionshortenedurl.repositories;

import com.betulsahin.auctionshortenedurl.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
