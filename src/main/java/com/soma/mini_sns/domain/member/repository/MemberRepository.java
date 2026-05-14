package com.soma.mini_sns.domain.member.repository;

import com.soma.mini_sns.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// <Member,Long> : Member 엔티티를 관리하고 PK는 Long 타입이다!
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // 회원가입 및 로그인 시 유저이름으로 존재성 파악용
    boolean existsByEmail(String email);

    // 이름 존재성 파악용
    boolean existsByName(String name);

    // 유저이름으로 회원 조회
    Optional<Member> findByEmail(String email);
}
