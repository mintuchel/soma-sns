package com.soma.mini_sns.domain.auth.dto.response;

import lombok.Builder;

@Builder
public record LoginResponse(
        String accessToken, // 서버에서 발급한 jwt
        String tokenType, // "Bearer"
        Long expiresIn // 만료 시간
) { }
