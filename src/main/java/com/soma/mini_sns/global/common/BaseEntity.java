package com.soma.mini_sns.global.common;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate // 엔티티가 처음 생성될때 자동으로 생성시간 넣어줌
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // 엔티티의 값이 변경될 때마다 수정시간을 자동으로 넣어줌
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable=true)
    private LocalDateTime deletedAt;
}
