package com.soma.mini_sns.domain.post.dto.response;

import com.soma.mini_sns.domain.post.entity.Post;

import java.time.LocalDateTime;

public record PostInfoResponse(
        Long postId,
        String author,
        String author_email,
        String title,
        String description,
        String imageUrl,
        LocalDateTime createdAt
) { }
