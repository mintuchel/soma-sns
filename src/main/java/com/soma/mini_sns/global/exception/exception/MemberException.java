package com.soma.mini_sns.global.exception.exception;

import com.soma.mini_sns.global.exception.errorCode.MemberErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberException extends RuntimeException {
    private MemberErrorCode userErrorCode;
}
