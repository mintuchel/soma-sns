package com.soma.mini_sns.domain.post.mapper;

import com.soma.mini_sns.domain.post.dto.response.PostInfoResponse;
import com.soma.mini_sns.domain.post.dto.response.PostSummaryResponse;
import com.soma.mini_sns.domain.post.entity.Post;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostMapper {

    @Insert("""
        INSERT INTO posts (
            member_id, 
            author, 
            title, 
            description, 
            image_url
        ) VALUES (
            #{memberId}, 
            #{author}, 
            #{title}, 
            #{description}, 
            #{imageUrl}
        )
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Post post);

    @Select("""
        SELECT
            id AS post_id,
            author,
            title,
            image_url,
            created_at
        FROM posts
        WHERE deleted_at IS NULL
        ORDER BY created_at DESC
    """)
    List<PostSummaryResponse> findAll();

    @Select("""
        SELECT
            id AS post_id,
            author,
            title,
            description,
            image_url,
            created_at
        FROM posts
        WHERE id = #{id} AND deleted_at IS NULL
    """)
    Optional<PostInfoResponse> findById(@Param("id") Long id);

    @Select("""
        SELECT
            id AS post_id,
            author,
            title,
            image_url,
            created_at
        FROM posts
        WHERE member_id = #{memberId} AND deleted_at IS NULL
        ORDER BY created_at DESC
    """)
    List<PostSummaryResponse> findByMemberId(@Param("memberId") Long memberId);
}
