package com.soma.mini_sns.domain.post.dto.response;

import com.soma.mini_sns.domain.post.entity.Post;

import java.time.LocalDateTime;

public record PostSummaryResponse(
        Long postId,
        String author,
        String title,
        String imageUrl,
        LocalDateTime createdAt
) {
    public static PostSummaryResponse from(Post post) {
        return new PostSummaryResponse(
                post.getId(),
                post.getAuthor(),
                post.getTitle(),
                post.getImageUrl(),
                post.getCreatedAt()
        );
    }
}
