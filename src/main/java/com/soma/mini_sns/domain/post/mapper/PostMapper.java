package com.soma.mini_sns.domain.post.mapper;

import com.soma.mini_sns.domain.post.dto.response.PostInfoResponse;
import com.soma.mini_sns.domain.post.dto.response.PostSummaryResponse;
import com.soma.mini_sns.domain.post.entity.Post;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostMapper {

    void insert(Post post);

    List<PostSummaryResponse> findAll();

    Optional<PostInfoResponse> findById(@Param("id") Long id);
}
