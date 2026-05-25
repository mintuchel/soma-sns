package com.soma.mini_sns.domain.post.entity;

import com.soma.mini_sns.global.common.BaseEntity;
import lombok.*;

/**
 * MyBatis는 ORM 기술이 아니므로 DB 스키마와 연관관계를 코드 단에서 정의하지 못함!
 * 데이터베이스 패러다임과 객체지향 패러다임의 불일치 문제는 감수하고 간다.
 */

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post extends BaseEntity {
    private Long id;
    private Long authorId;
    private String authorName;
    private String title;
    private String description;
    private String imageUrl;
}