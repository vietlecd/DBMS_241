package com.project.shopapp.services.impl;

import com.project.shopapp.components.JwtTokenUtil;
import com.project.shopapp.customexceptions.InvalidParamException;
import com.project.shopapp.models.Token;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.TokenRepository;
import com.project.shopapp.utils.CheckExistedUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final CheckExistedUtils checkExistedUtils;

    public void saveToken(User user, String token, String tokenType, LocalDateTime expirationDate) {
        Token newToken = Token.builder()
                .token(token)
                .tokenType(tokenType)
                .expirationDate(expirationDate)
                .revoked(false)
                .user(user)
                .build();

        tokenRepository.save(newToken);
    }

    public String generateRefreshToken(User user) throws InvalidParamException {
        String refreshToken = jwtTokenUtil.generateToken(user, "REFRESH");
        LocalDateTime expirationDate = LocalDateTime.now().plusSeconds(jwtTokenUtil.refreshExpiration);
        saveToken(user, refreshToken, "REFRESH", expirationDate);
        return refreshToken;
    }

    public String generateAccessToken(User user) throws InvalidParamException {
        String accessToken = jwtTokenUtil.generateToken(user, "ACCESS");
        LocalDateTime expirationDate = LocalDateTime.now().plusSeconds(jwtTokenUtil.accessExpiration);
        saveToken(user, accessToken, "ACCESS", expirationDate);
        return accessToken;
    }

    public void invalidateToken(Token storedToken) {
        checkExistedUtils.checkObjectExisted(storedToken, "Token");

        storedToken.setRevoked(true);
        tokenRepository.save(storedToken);

        if ("REFRESH".equals(storedToken.getTokenType()) && storedToken.getLinkedAccessToken() != null) {
            Token linkedAccessToken = storedToken.getLinkedAccessToken();
            linkedAccessToken.setRevoked(true);
            tokenRepository.save(linkedAccessToken);
        }
    }

    public String refreshAccessToken(String refreshToken) throws InvalidParamException {
        Token storedRefreshToken = tokenRepository.findByToken(refreshToken);
        if (storedRefreshToken.getTokenType().equals("ACCESS")) {
            throw new InvalidParamException("Only access token");
        }
        checkExistedUtils.checkObjectExisted(storedRefreshToken, "Refresh Token");

        if (storedRefreshToken.isRevoked() || storedRefreshToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh Token is invalid or expired");
        }

        Token linkedAccessToken = storedRefreshToken.getLinkedAccessToken();
        if (linkedAccessToken != null && !linkedAccessToken.isRevoked()) {
            linkedAccessToken.setRevoked(true);
            tokenRepository.save(linkedAccessToken);
        }

        String newAccessToken = jwtTokenUtil.generateToken(storedRefreshToken.getUser(), "ACCESS");
        LocalDateTime expirationDate =  LocalDateTime.now().plusSeconds(jwtTokenUtil.accessExpiration);

        Token newAccessTokenModel = Token.builder()
                .token(newAccessToken)
                .tokenType("ACCESS")
                .expirationDate(expirationDate)
                .revoked(false)
                .user(storedRefreshToken.getUser())
                .build();

        tokenRepository.save(newAccessTokenModel);

        storedRefreshToken.setLinkedAccessToken(newAccessTokenModel);
        tokenRepository.save(storedRefreshToken);

        return newAccessToken;
    }
}
