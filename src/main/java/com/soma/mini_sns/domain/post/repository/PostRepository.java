package com.soma.mini_sns.domain.post.repository;

import com.soma.mini_sns.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// <Post, Long> : Post 엔티티를 관리하고 PK는 Long 타입이다
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {


    // 최신 게시글 목록 조회 (삭제되지 않고, 최신순, 작성자 닉네임 포함)
    @Query(value= """
                SELECT
                    id,
                    author,
                    title,
                    imageUrl,
                    created_at
                FROM posts
                WHERE deleted_at IS NULL
                ORDER BY created_at DESC
            """
            , nativeQuery = true
    )
    List<Post> findAll();

    // 특정 게시글 조회 (삭제되지 않고, 작성자 정보 포함)
    @Query(value= """
                SELECT
                    id,
                    author,
                    member_id AS author_id,
                    members.email AS author_email,
                    title,
                    description,
                    imageUrl,
                    created_at,
                    updated_at
                FROM posts
                JOIN members ON post.member_id = members.id
                WHERE deleted_at IS NULL
            """
    )
    Optional<Post> findById(Long id);

    // 특정 회원이 작성한 게시글 조회
    List<Post> findByMemberIdAndDeletedAtIsNull(Long memberId);
}
