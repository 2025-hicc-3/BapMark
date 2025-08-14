package com.example.demo.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import jakarta.servlet.http.HttpServletRequest;
import io.jsonwebtoken.*;

@Component
public class JwtProvider {

    private final String secretKey = "your-very-secret-key-your-very-secret-key"; // 길게!
    private final Key key = Keys.hmacShaKeyFor(secretKey.getBytes()); // secretKey 기반 key 생성
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1시간

    public String generateToken(String oauthId, String email) {
        return Jwts.builder()
                .setSubject(email)
                .claim("oauthId", oauthId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token); // ✅ 여기
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }
}
