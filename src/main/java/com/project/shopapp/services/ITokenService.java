package com.project.shopapp.services;

public interface ITokenService {
    String generateAccessToken(String username);

    boolean validateRefreshToken(String token);

    String refreshAccessToken(String refreshTokenStr);

    void invalidateToken(String token);
}
