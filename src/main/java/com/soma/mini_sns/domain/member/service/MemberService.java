package com.soma.mini_sns.domain.member.service;

import com.soma.mini_sns.domain.member.dto.request.SignUpRequest;
import com.soma.mini_sns.domain.member.dto.response.MemberInfoResponse;
import com.soma.mini_sns.domain.member.dto.response.SignUpResponse;
import com.soma.mini_sns.domain.member.entity.Member;
import com.soma.mini_sns.domain.member.mapper.MemberMapper;
import com.soma.mini_sns.global.exception.errorCode.MemberErrorCode;
import com.soma.mini_sns.global.exception.exception.MemberException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberMapper memberMapper;

    @Value("${jwt.secret}")
    private String secretKey;

    @Transactional(readOnly = true)
    public MemberInfoResponse getUserById(long id) {
        return memberMapper.findById(id)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public MemberInfoResponse getMyInfo(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String email = claims.getSubject();

        return memberMapper.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        if (memberMapper.existsByEmail(request.email())) {
            throw new MemberException(MemberErrorCode.DUPLICATE_EMAIL);
        }
        if (memberMapper.existsByName(request.name())) {
            throw new MemberException(MemberErrorCode.DUPLICATE_NAME);
        }

        String encodedPassword = passwordEncoder.encode(request.password());

        Member member = Member.builder()
                .email(request.email())
                .name(request.name())
                .password(encodedPassword)
                .build();

        memberMapper.insert(member); // useGeneratedKeys로 member.id 자동 세팅
        
        return SignUpResponse.from(member);
    }
}
