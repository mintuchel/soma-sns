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
                    author_id,
                    author_name,
                    title,
                    description,
                    image_url
                ) VALUES (
                    #{authorId},
                    #{authorName},
                    #{title},
                    #{description},
                    #{imageUrl}
                )
            """)
    // DB에서 생성한 PK값을 반환받아 Post 객체의 id 필드에 자동으로 매핑해줌
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Post post);

    @Select("""
                SELECT
                    id AS post_id,
                    author_name,
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
                    p.id AS post_id,
                    p.author_id,
                    m.name AS author_name,
                    m.email AS author_email,
                    p.title,
                    p.description,
                    p.image_url,
                    p.created_at
                FROM posts p
                JOIN members m ON m.id = p.author_id
                WHERE p.id = #{id} AND p.deleted_at IS NULL
            """)
    Optional<PostInfoResponse> findById(@Param("id") Long id);
}
