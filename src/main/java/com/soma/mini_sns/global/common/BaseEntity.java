package com.soma.mini_sns.global.common;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 모든 엔티티의 공통 필드인 createdAt, updatedAt, deletedAt을 포함하는 추상 클래스
 * 모든 엔티티는 해당 클래스를 상속받고 시작
 */
@Getter
public abstract class BaseEntity {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
