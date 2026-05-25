package com.soma.mini_sns.domain.post.controller;

import com.soma.mini_sns.domain.post.dto.request.PostRequest;
import com.soma.mini_sns.domain.post.dto.response.MyPostsResponse;
import com.soma.mini_sns.domain.post.dto.response.PostInfoResponse;
import com.soma.mini_sns.domain.post.dto.response.PostSummaryResponse;
import com.soma.mini_sns.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@Tag(name = "게시글 API", description = "게시글 작성/조회/수정/삭제")
public class PostController {

    private final PostService postService;

    // 최신순, 작성자 닉네임 포함
    @GetMapping("")
    @Operation(summary = "게시글 목록 조회")
    public ResponseEntity<List<PostSummaryResponse>> getPosts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.getPosts());
    }

    // 작성자 정보 포함
    @GetMapping("/{postId}")
    @Operation(summary = "게시글 상세 조회")
    public ResponseEntity<PostInfoResponse> getPost(@PathVariable Long postId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.getPost(postId));
    }

    @GetMapping("/me")
    @Operation(summary = "내 게시글 조회")
    public ResponseEntity<MyPostsResponse> getMyPosts(
            @RequestHeader("Authorization") String authorizationHeader) {
        
        String token = authorizationHeader.substring(7);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.getMyPosts(token));
    }

    @PostMapping("")
    @Operation(summary = "게시글 작성")
    public ResponseEntity<Void> createPost(
            @RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody PostRequest request) {
        String token = authorizationHeader.substring(7);
        postService.createPost(token, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "게시글 수정")
    public ResponseEntity<Void> updatePost(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long postId,
            @Valid @RequestBody PostRequest request) {
        String token = authorizationHeader.substring(7);
        postService.updatePost(token, postId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "게시글 삭제")
    public ResponseEntity<Void> deletePost(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long postId) {
        String token = authorizationHeader.substring(7);
        postService.deletePost(token, postId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
