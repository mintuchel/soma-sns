package com.soma.mini_sns.domain.post.service;

import com.soma.mini_sns.domain.member.dto.response.MemberInfoResponse;
import com.soma.mini_sns.domain.member.mapper.MemberMapper;
import com.soma.mini_sns.domain.post.dto.request.PostRequest;
import com.soma.mini_sns.domain.post.dto.response.PostInfoResponse;
import com.soma.mini_sns.domain.post.dto.response.PostSummaryResponse;
import com.soma.mini_sns.domain.post.entity.Post;
import com.soma.mini_sns.domain.post.mapper.PostMapper;
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

    private final PostMapper postMapper;
    private final MemberMapper memberMapper;

    @Value("${jwt.secret}")
    private String secretKey;

    @Transactional(readOnly = true)
    public List<PostSummaryResponse> getPosts() {
        return postMapper.findAll();
    }

    @Transactional(readOnly = true)
    public PostInfoResponse getPost(Long postId) {
        return postMapper.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));
    }

    @Transactional
    public void createPost(String token, PostRequest request) {
        MemberInfoResponse member = getMemberFromToken(token);

        Post post = Post.builder()
                .authorId(member.id())
                .authorName(member.name())
                .title(request.title())
                .description(request.description())
                .imageUrl(request.imageUrl())
                .build();

        postMapper.insert(post);
    }

    private MemberInfoResponse getMemberFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String email = claims.getSubject();

        return memberMapper.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}
