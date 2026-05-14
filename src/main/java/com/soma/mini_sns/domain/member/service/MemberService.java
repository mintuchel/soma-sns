package com.soma.mini_sns.domain.member.service;

import com.soma.mini_sns.domain.member.dto.request.SignUpRequest;
import com.soma.mini_sns.domain.member.dto.response.MemberInfoResponse;
import com.soma.mini_sns.domain.member.dto.response.SignUpResponse;
import com.soma.mini_sns.domain.member.entity.Member;
import com.soma.mini_sns.domain.member.repository.MemberRepository;
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
import java.security.Key;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    @Transactional(readOnly = true)
    public MemberInfoResponse getUserById(long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        return MemberInfoResponse.from(member);
    }

    @Transactional(readOnly = true)
    public MemberInfoResponse getMyInfo(String token){

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        // 우리쪽에서 발급한 토큰이 맞는지 인증
        // 그리고 유저 특정 정보가 있는 Payload 파싱하기
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        // Payload에서 email 정보 추출
        String email = claims.getSubject();

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        return MemberInfoResponse.from(member);
    }

    @Transactional
    public SignUpResponse signUp(SignUpRequest request){

        // 닉네임 중복 검사
        if (memberRepository.existsByName(request.name())) {
            throw new MemberException(MemberErrorCode.DUPLICATE_NAME);
        }

        // 이메일 중복 검사
        if (memberRepository.existsByEmail(request.email())) {
            throw new MemberException(MemberErrorCode.DUPLICATE_EMAIL);
        }

        // 비밀번호 해싱하여 저장하기
        String encodedPassword = passwordEncoder.encode(request.password());

        Member member = Member.builder()
                .email(request.email())
                .name(request.name())
                .password(encodedPassword)
                .build();

        Member savedMember = memberRepository.save(member);

        return SignUpResponse.from(savedMember);
    }
}
