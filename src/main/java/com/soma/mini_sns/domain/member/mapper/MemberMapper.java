package com.soma.mini_sns.domain.member.mapper;

import com.soma.mini_sns.domain.member.dto.response.MemberInfoResponse;
import com.soma.mini_sns.domain.member.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    void insert(Member member);

    boolean existsByEmail(@Param("email") String email);

    boolean existsByName(@Param("name") String name);

    Optional<MemberInfoResponse> findById(@Param("id") Long id);

    Optional<MemberInfoResponse> findByEmail(@Param("email") String email);

    Optional<Member> findByEmailWithPassword(@Param("email") String email);
}
