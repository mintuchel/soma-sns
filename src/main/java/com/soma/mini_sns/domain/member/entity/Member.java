package com.soma.mini_sns.domain.member.entity;

import com.soma.mini_sns.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity // JPA 엔티티로 설정해서 ORM 기술을 적용할 스키마(클래스)임을 명시
@Getter
@Builder
@NoArgsConstructor(access= AccessLevel.PROTECTED) // 기본 생성자 허용. JPA가 빈 깡통 객체를 생성할 수 있도록. JPA만 허용하도록 AccessLevel을 PROTECTED로
@AllArgsConstructor
@Table(name="members")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에 PK값 생성을 위임함
    private Long id; // long 대신 nullable이 가능한 Long을 사용하여 JPA에게 식별자 없음 상태를 표현해줄 수 있음

    @Column(nullable=false, unique=true, length=20)
    private String name;

    @Column(nullable=false, length=255)
    private String password;

    @Column(nullable=false, unique=true)
    private String email;
}
