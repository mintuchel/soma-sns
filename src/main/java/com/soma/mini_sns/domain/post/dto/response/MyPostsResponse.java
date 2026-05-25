package com.soma.mini_sns.domain.post.dto.response;

import com.soma.mini_sns.domain.post.entity.Post;

import java.util.List;

public record MyPostsResponse(
        List<PostSummaryResponse> posts
) {
    public static MyPostsResponse from(List<Post> posts) {
        List<PostSummaryResponse> summaries = posts.stream()
                .map(PostSummaryResponse::from)
                .toList();
        return new MyPostsResponse(summaries);
    }
}
