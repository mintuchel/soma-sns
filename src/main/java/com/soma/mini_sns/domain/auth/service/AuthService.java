package com.soma.mini_sns.domain.auth.service;

import com.soma.mini_sns.domain.auth.dto.request.LoginRequest;
import com.soma.mini_sns.domain.auth.dto.response.LoginResponse;
import com.soma.mini_sns.domain.member.entity.Member;
import com.soma.mini_sns.domain.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secretKey;

    private final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60L; // 6시간

    // 고민한 부분
    // 쿼리 두번 날리기 vs 쿼리 한번 날리지만 다 가져오기
    @Transactional(readOnly=true)
    public LoginResponse login(LoginRequest request) {

        // 유저 존재 여부 확인
        if (!memberRepository.existsByEmail(request.email())) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }

        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 비밀번호 일치 여부 확인
        if(!passwordEncoder.matches(request.password(),member.getPassword())) {
            throw new MemberException(MemberErrorCode.INVALID_PASSWORD);
        }

        // 인증 통과되었으면 토큰 발급해주기
        String accessToken = createToken(member);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .expiresIn(ACCESS_TOKEN_EXPIRE_TIME / 1000)
                .build();
    }

    // JWT 생성 함수
    private String createToken(Member member){
        Date issuedAt = new Date();
        Date expiredAt = new Date(issuedAt.getTime() + ACCESS_TOKEN_EXPIRE_TIME);

        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        // 모든 설정 정보는 Payload에 담긴다
        return Jwts.builder()
                .setSubject(member.getEmail())
                .claim("role","MEMBER")
                .claim("name", member.getName())
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
