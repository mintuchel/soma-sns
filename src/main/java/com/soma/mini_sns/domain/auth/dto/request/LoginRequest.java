package com.soma.mini_sns.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "로그인 요청")
public record LoginRequest(
        @Email(message = "유효한 이메일 형식을 입력하세요.")
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Size(message="이메일은 RCF형식에 따라 255자 이하여야 합니다.", max=255)
        @Schema(description = "이메일 주소", example = "max3627@naver.com")
        String email,

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Size(message = "비밀번호는 8자 이상 20자 이하여야 합니다.", min = 8, max = 20)
        @Schema(description = "비밀번호", example = "qwer1234!")
        String password
) { }
