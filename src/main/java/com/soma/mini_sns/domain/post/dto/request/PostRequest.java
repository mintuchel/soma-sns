package com.soma.mini_sns.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시글 생성 요청")
public record PostRequest(
        @Schema(description = "게시글 제목", example = "테스트 제목") String title,

        @Schema(description = "게시글 본문", example = "테스트 본문") String description,

        @Schema(description = "이미지 URL", example = "테스트 이미지 URL") String imageUrl) {
}
