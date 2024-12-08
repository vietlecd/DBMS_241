package com.project.shopapp.repositories;

import com.project.shopapp.models.Token;
import com.project.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Token findByToken(String token);

    Token findByTokenAndRevoked(String token, Boolean revoked);

    List<Token> findAllByUserAndRevoked(User user, Boolean revoked);
}
