package com.example.bucketplace.domain.member.repository;

import com.example.bucketplace.domain.member.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    void deleteByToken(String token);
}
