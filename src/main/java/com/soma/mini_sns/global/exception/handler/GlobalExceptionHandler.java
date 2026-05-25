package com.soma.mini_sns.global.exception.handler;

import com.soma.mini_sns.global.exception.exception.MemberException;
import com.soma.mini_sns.global.exception.exception.PostException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Controller > Dispatcher로 가는 도중에 낚아채줌
 * ViewResolver까지는 가지도 않고 바로 Client에게 응답을 보냄!
 */

// 모든 Controller에서 발생하는 예외를 한 곳에서 감시하다가, 예외가 터지면 처리해줌
// ControllerAdvice + ResponseBody가 합쳐진 형태라, 응답을 바로 JSON 형태나 문자열로 보낼 수 있음
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MemberException.class)
    protected ResponseEntity<String> handleUserException(MemberException e) {

        return ResponseEntity
                .status(e.getUserErrorCode().getHttpStatus())
                .body(e.getUserErrorCode().getMessage());
    }

    @ExceptionHandler(value = PostException.class)
    protected ResponseEntity<String> handlePostException(PostException e) {

        return ResponseEntity
                .status(e.getPostErrorCode().getHttpStatus())
                .body(e.getPostErrorCode().getMessage());
    }
}
