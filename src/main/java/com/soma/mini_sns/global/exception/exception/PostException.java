package com.soma.mini_sns.global.exception.exception;

import com.soma.mini_sns.global.exception.errorCode.PostErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostException extends RuntimeException {
    private PostErrorCode postErrorCode;
}
