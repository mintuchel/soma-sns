package com.soma.mini_sns.domain.member.entity;

import com.soma.mini_sns.global.common.BaseEntity;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity {
    private Long id;
    private String name;
    private String password;
    private String email;
}
