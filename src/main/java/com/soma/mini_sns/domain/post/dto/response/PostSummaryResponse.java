package com.soma.mini_sns.domain.post.dto.response;

import java.time.LocalDateTime;

public record PostSummaryResponse(
                Long postId,
                String authorName,
                String title,
                String imageUrl,
                LocalDateTime createdAt) {
}
