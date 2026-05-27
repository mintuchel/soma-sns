package com.soma.mini_sns.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

/**
 * 들어온 JWT를 우리쪽에서 발급한 토큰이 맞는지 검증하고, Claim 정보를 디코딩하여 추출하는 역할을 함.
 * 1. verifyWith(): header에 적힌 해시 알고리즘과 SecretKey를 가지고 직접 Signature를 만들어 본 후, 받은
 * JWT의 Signature와 동일한지 검증함.
 * 2. parseSignedClaims(): JWT의 인코딩 된 payload 부분을 디코딩하여 Claims 객체로 변환하여 원본 정보를
 * 가져옴.
 */
@Component
public class JwtTokenProvider {

    private final SecretKey key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String extractEmail(String token) {
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
