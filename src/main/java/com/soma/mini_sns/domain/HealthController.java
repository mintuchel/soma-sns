package com.soma.mini_sns.domain;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
@Tag(name = "Health Check", description = "서버 상태 확인용 API")
public class HealthController {

    @GetMapping("")
    @Operation(summary = "health-check")
    public String healthCheck() {
        return "Server Running!";
    }
}
