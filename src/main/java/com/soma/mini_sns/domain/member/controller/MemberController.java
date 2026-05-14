package com.soma.mini_sns.domain.member.controller;

import com.soma.mini_sns.domain.member.dto.request.SignUpRequest;
import com.soma.mini_sns.domain.member.dto.response.MemberInfoResponse;
import com.soma.mini_sns.domain.member.dto.response.SignUpResponse;
import com.soma.mini_sns.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@Tag(name = "회원 API", description = "회원가입/정보 조회")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("")
    @Operation(summary="회원가입")
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(memberService.signUp(signUpRequest));
    }

    @GetMapping("/me")
    @Operation(summary="내 정보 조회")
    public ResponseEntity<MemberInfoResponse> getMyInfo(@RequestHeader("Authorization") String authorizationHeader){
        // "Bearer" 접두사 제거
        String token = authorizationHeader.substring(7);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.getMyInfo(token));
    }
}
