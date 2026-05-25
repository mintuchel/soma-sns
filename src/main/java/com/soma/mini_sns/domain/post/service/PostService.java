package com.soma.mini_sns.domain.post.service;

import com.soma.mini_sns.domain.member.entity.Member;
import com.soma.mini_sns.domain.member.repository.MemberRepository;
import com.soma.mini_sns.domain.post.dto.request.PostRequest;
import com.soma.mini_sns.domain.post.dto.response.MyPostsResponse;
import com.soma.mini_sns.domain.post.dto.response.PostInfoResponse;
import com.soma.mini_sns.domain.post.dto.response.PostSummaryResponse;
import com.soma.mini_sns.domain.post.entity.Post;
import com.soma.mini_sns.domain.post.repository.PostRepository;
import com.soma.mini_sns.global.exception.errorCode.MemberErrorCode;
import com.soma.mini_sns.global.exception.errorCode.PostErrorCode;
import com.soma.mini_sns.global.exception.exception.MemberException;
import com.soma.mini_sns.global.exception.exception.PostException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    // 최신 게시글 목록 조회
    @Transactional(readOnly = true)
    public List<PostSummaryResponse> getPosts() {
        return postRepository.findAllByDeletedAtIsNull().stream()
                .map(PostSummaryResponse::from)
                .toList();
    }

    // 특정 게시글 정보 조회
    @Transactional(readOnly = true)
    public PostInfoResponse getPost(Long postId) {
        Post post = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        return PostInfoResponse.from(post);
    }

    @Transactional(readOnly = true)
    public MyPostsResponse getMyPosts(String token) {
        Member member = getMemberFromToken(token);
        List<Post> posts = postRepository.findByMemberIdAndDeletedAtIsNull(member.getId());

        return MyPostsResponse.from(posts);
    }

    @Transactional
    public void createPost(String token, PostRequest request) {
        Member member = getMemberFromToken(token);

        Post post = Post.builder()
                .member(member)
                .author(member.getName())
                .title(request.title())
                .description(request.description())
                .imageUrl(request.imageUrl())
                .build();

        postRepository.save(post);
    }

    @Transactional
    public void updatePost(String token, Long postId, PostRequest request) {
        Member member = getMemberFromToken(token);

        Post post = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        // 작성자 본인 확인
        if (!post.getMember().getId().equals(member.getId())) {
            throw new PostException(PostErrorCode.UNAUTHORIZED_POST_ACCESS);
        }

        post.update(request.title(), request.description(), request.imageUrl());
    }

    @Transactional
    public void deletePost(String token, Long postId) {
        Member member = getMemberFromToken(token);

        Post post = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        // 작성자 본인 확인
        if (!post.getMember().getId().equals(member.getId())) {
            throw new PostException(PostErrorCode.UNAUTHORIZED_POST_ACCESS);
        }

        postRepository.delete(post);
    }

    private Member getMemberFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String email = claims.getSubject();

        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}
