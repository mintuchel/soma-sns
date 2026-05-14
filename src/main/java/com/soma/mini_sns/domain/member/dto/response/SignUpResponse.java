package com.soma.mini_sns.domain.member.dto.response;

import com.soma.mini_sns.domain.member.entity.Member;

public record SignUpResponse(
        long id,
        String email,
        String name
) {
    public static SignUpResponse from(Member member) {
        return new SignUpResponse(
                member.getId(),
                member.getEmail(),
                member.getName()
        );
    }
}
