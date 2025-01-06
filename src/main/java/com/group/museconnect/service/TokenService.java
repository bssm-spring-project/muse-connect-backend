package com.group.museconnect.service;

import com.group.museconnect.domain.user.User;
import com.group.museconnect.global.config.properties.JwtProperties;
import com.group.museconnect.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final JwtProperties jwtProperties;
    private final UserRepository userRepository;
    private final UserService userService;

    public String generateAccessToken(String id) {
        Claims claims = Jwts.claims();
        claims.put("id", id);

        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam("typ", "jwt")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.getAccessExpirationTime()))
                .signWith(getSigningKey(jwtProperties.getSecretKey()), SignatureAlgorithm.HS256)
                .compact();

    }

    public User getUser(String token) {
        return userService.getUserByEmail(getId(token));
    }

    public String getId(String token) {
        return extractAllClaims(token).get("id", String.class);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(jwtProperties.getSecretKey()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("Expired or invalid JWT token");
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
