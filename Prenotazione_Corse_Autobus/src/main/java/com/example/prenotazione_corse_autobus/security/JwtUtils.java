package com.example.prenotazione_corse_autobus.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.accessTokenExpirationMs}")
    private int accessTokenExpirationMs;

    @Value("${jwt.refreshTokenExpirationMs}")
    private long refreshTokenExpirationMs;

    /** Costruisco la chiave dai bytes della secret (non base64) */
    private Key getSigningKey() {
        // Per HS256 servono >= 256 bit (>= 32 char). Metti una secret lunga.
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateJwtToken(String username) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + accessTokenExpirationMs);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String generateRefreshToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, refreshTokenExpirationMs);
    }

    private String createToken(Map<String, Object> claims, String subject, long expirationMs) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Unable to validate JWT token: {}", e.getMessage());
            return false;
        }
    }

    public Claims decodeRefreshToken(String refreshToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();
        } catch (JwtException e) {
            logger.error("Invalid refresh token: {}", e.getMessage());
            return null;
        }
    }
}
