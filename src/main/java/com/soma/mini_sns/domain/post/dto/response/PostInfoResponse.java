package com.soma.mini_sns.domain.post.dto.response;

import com.soma.mini_sns.domain.post.entity.Post;

import java.time.LocalDateTime;

public record PostInfoResponse(
        Long postId,
        String author,
        String title,
        String description,
        String imageUrl,
        LocalDateTime createdAt
) {
    public static PostInfoResponse from(Post post) {
        return new PostInfoResponse(
                post.getId(),
                post.getAuthor(),
                post.getTitle(),
                post.getDescription(),
                post.getImageUrl(),
                post.getCreatedAt()
        );
    }
}
