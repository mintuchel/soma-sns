package com.soma.mini_sns.global.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 자바의 Enum은 C/C++과 달리 특수한 "클래스"이다.
 * 변수와 메서드를 가질 수 있다.
 * 상수마다 고유한 상태를 가진다. 상수 하나하나가 사실은 Enum 클래스 인스턴스(객체)이다!
 * 싱글톤을 보장한다. 각 상수 값마다 객체 하나가 재사용된다.
 */
@Getter
@RequiredArgsConstructor
public enum MemberErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 이메일(유저)입니다"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 옳지 않습니다"),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다"),
    DUPLICATE_NAME(HttpStatus.CONFLICT, "이미 사용 중인 닉네임입니다");

    private final HttpStatus httpStatus;
    private final String message;
}