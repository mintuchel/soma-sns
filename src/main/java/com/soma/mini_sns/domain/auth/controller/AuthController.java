package com.soma.mini_sns.domain.auth.controller;

import com.soma.mini_sns.domain.auth.dto.request.LoginRequest;
import com.soma.mini_sns.domain.auth.dto.response.LoginResponse;
import com.soma.mini_sns.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "로그인 API", description = "인증인가 - JWT 검증 및 발행")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary="로그인")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.login(loginRequest));
    }
}
