package com.soma.mini_sns.domain.post.dto.response;

import java.time.LocalDateTime;

public record PostInfoResponse(
        Long postId,
        Long authorId,
        String authorName,
        String authorEmail,
        String title,
        String description,
        String imageUrl,
        LocalDateTime createdAt) {
}
