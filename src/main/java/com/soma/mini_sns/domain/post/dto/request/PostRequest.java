package com.soma.mini_sns.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시글 작성/수정 요청")
public record PostRequest(
        @Schema(description = "게시글 제목")
        String title,

        @Schema(description = "게시글 본문")
        String description,

        @Schema(description = "이미지 URL")
        String imageUrl
) {}
