package com.soma.mini_sns.global.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다"),
    UNAUTHORIZED_POST_ACCESS(HttpStatus.FORBIDDEN, "해당 게시글에 대한 접근 권한이 없습니다");

    private final HttpStatus httpStatus;
    private final String message;
}
