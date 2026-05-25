package com.soma.mini_sns.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    /** Swagger UI Authorize / @SecurityRequirement 에서 동일한 이름으로 사용 */
    public static final String BEARER_JWT = "bearer-jwt";

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme bearer = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);

        return new OpenAPI()
                .info(new Info().title("mini-sns API").version("v1"))
                .components(new Components().addSecuritySchemes(BEARER_JWT, bearer))
                // 전역 적용: Swagger UI에 Authorize 버튼이 뜨고, 모든 요청에 토큰이 자동으로 첨부됨
                .addSecurityItem(new SecurityRequirement().addList(BEARER_JWT));
    }
}
