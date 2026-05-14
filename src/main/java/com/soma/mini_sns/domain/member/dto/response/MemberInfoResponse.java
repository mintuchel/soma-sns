package com.soma.mini_sns.domain.member.dto.response;

import com.soma.mini_sns.domain.member.entity.Member;

public record MemberInfoResponse(
        long id,
        String name,
        String email
) {
    public static MemberInfoResponse from(Member member) {
        return new MemberInfoResponse(
                member.getId(),
                member.getName(),
                member.getEmail()
        );
    }
}