package com.project.shopapp.components;

import com.project.shopapp.customexceptions.InvalidParamException;
import com.project.shopapp.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    @Value("${jwt.expiration}")
    public int accessExpiration;

    @Value("${jwt.refresh_expiration}")
    public int refreshExpiration;

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateToken(User user, String tokenType) throws InvalidParamException {
        int expirationTime = "REFRESH".equalsIgnoreCase(tokenType) ? refreshExpiration : accessExpiration;

        try {
            return Jwts.builder()
                    .setClaims(Map.of("username", user.getUsername(), "tokenType", tokenType))
                    .setSubject(user.getUsername())
                    .setExpiration(new Date(System.currentTimeMillis() + expirationTime * 1000L))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            throw new InvalidParamException("Cannot create JWT token, error: " + e.getMessage());
        }
    }

    private Key getSignInKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        //Keys.hmacShaKeyFor(Decoders.BASE64.decode("TaqlmGv1iEDMRiFp/pHuID1+T84IABfuA0xXh4GhiUI="));
        return Keys.hmacShaKeyFor(bytes);
    }


//    private String generateSecretKey() {
//        SecureRandom random = new SecureRandom();
//        byte[] keyBytes = new byte[32]; // 256-bit key
//        random.nextBytes(keyBytes);
//        String secretKey = Encoders.BASE64.encode(keyBytes);
//        return secretKey;
//    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //check expiration
    public boolean isTokenExpired(String token) {
        Date expirationDate = this.extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractTokenType(String token) {
        return extractClaim(token, claims -> (String) claims.get("tokenType"));
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()))
                && !isTokenExpired(token); //check hạn của token
    }
}
