package com.soma.mini_sns.domain.auth.service;

import com.soma.mini_sns.domain.auth.dto.request.LoginRequest;
import com.soma.mini_sns.domain.auth.dto.response.LoginResponse;
import com.soma.mini_sns.domain.member.entity.Member;
import com.soma.mini_sns.domain.member.mapper.MemberMapper;
import com.soma.mini_sns.global.exception.errorCode.MemberErrorCode;
import com.soma.mini_sns.global.exception.exception.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.security.Key;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secretKey;

    private final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60L;

    @Transactional(readOnly=true)
    public LoginResponse login(LoginRequest request) {
        Member member = memberMapper.findByEmailWithPassword(request.email())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new MemberException(MemberErrorCode.INVALID_PASSWORD);
        }

        String accessToken = createToken(member);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .expiresIn(ACCESS_TOKEN_EXPIRE_TIME / 1000)
                .build();
    }

    private String createToken(Member member) {
        Date issuedAt = new Date();
        Date expiredAt = new Date(issuedAt.getTime() + ACCESS_TOKEN_EXPIRE_TIME);

        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.builder()
                .setSubject(member.getEmail())
                .claim("role", "MEMBER")
                .claim("name", member.getName())
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
