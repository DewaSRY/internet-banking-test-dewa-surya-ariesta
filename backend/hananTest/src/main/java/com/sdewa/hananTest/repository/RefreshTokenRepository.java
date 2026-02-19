package com.sdewa.hananTest.repository;

import com.sdewa.hananTest.entity.RefreshToken;
import com.sdewa.hananTest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    
    Optional<RefreshToken> findByToken(String token);
    
    @Modifying
    void deleteByUser(User user);
    
    @Modifying
    void deleteByExpiryDateBefore(LocalDateTime now);
}
