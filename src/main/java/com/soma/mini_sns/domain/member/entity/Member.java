package com.soma.mini_sns.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    // 이메일
    @Column(nullable = false, unique = true, length = 30)
    private String email;

    // 비밀번호
    // encode 시 BCrypt로 60자 정도 길이의 해싱된 비번 반환. 여유있게 100자로 제한
    @Column(nullable = false, length = 100)
    private String password;

    // 닉네임
    @Column(nullable = false)
    private String username;
}
