package com.soma.mini_sns.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "회원가입 요청")
public record SignUpRequest(
        // 내부적인 정규식으로 통해 email 형식인지 판단
        @Email(message = "유효한 이메일 형식을 입력하세요.") @NotBlank(message = "이메일은 필수 입력 값입니다.") @Size(message = "이메일은 RCF형식에 따라 255자 이하여야 합니다.", max = 255) @Schema(description = "이메일 주소", example = "max3627@naver.com") String email,

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.") @Size(message = "비밀번호는 8자 이상 20자 이하여야 합니다.", min = 8, max = 20) @Schema(description = "비밀번호", example = "qwer1234!") String password,

        @NotBlank(message = "이름은 필수 입력 값입니다.") @Size(message = "이름은 2자 이상 10자 이하여야 합니다.", min = 2, max = 10) @Schema(description = "이름", example = "민재홍") String name) {
}
