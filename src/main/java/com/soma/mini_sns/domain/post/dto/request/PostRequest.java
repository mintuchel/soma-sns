package com.soma.mini_sns.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시글 생성 요청")
public record PostRequest(
        String title,
        String description,
        String imageUrl) {

    @Schema(description = "게시글 제목", example = "테스트 제목")
    @Override
    public String title() { return title; }

    @Schema(description = "게시글 본문", example = "테스트 본문")
    @Override
    public String description() { return description; }

    @Schema(description = "이미지 URL", example = "테스트 이미지 URL")
    @Override
    public String imageUrl() { return imageUrl; }
}
