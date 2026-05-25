package com.soma.mini_sns.domain.member.mapper;

import com.soma.mini_sns.domain.member.dto.response.MemberInfoResponse;
import com.soma.mini_sns.domain.member.entity.Member;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    @Insert("""
            INSERT INTO members (name, password, email, created_at, updated_at)
            VALUES (#{name}, #{password}, #{email}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Member member);

    @Select("SELECT EXISTS(SELECT 1 FROM members WHERE email = #{email})")
    boolean existsByEmail(@Param("email") String email);

    @Select("SELECT EXISTS(SELECT 1 FROM members WHERE name = #{name})")
    boolean existsByName(@Param("name") String name);

    @Select("""
            SELECT id, name, email
            FROM members
            WHERE id = #{id} AND deleted_at IS NULL
            """)
    Optional<MemberInfoResponse> findById(@Param("id") Long id);

    @Select("""
            SELECT id, name, email
            FROM members
            WHERE email = #{email} AND deleted_at IS NULL
            """)
    Optional<MemberInfoResponse> findByEmail(@Param("email") String email);

    @Select("""
            SELECT id, name, password, email,
                   created_at, updated_at, deleted_at
            FROM members
            WHERE email = #{email} AND deleted_at IS NULL
            """)
    Optional<Member> findByEmailWithPassword(@Param("email") String email);
}
