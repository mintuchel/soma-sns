package com.soma.mini_sns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/*
@SpringBootApplication
- @SpringBootConfiguration
- @EnableAutoConfiguration
- @ComponentScan

@EnableJpaAuditing
- JPA의 Auditing(감시/감사) 기능을 프로젝트 전체에 활성화
- BaseEntity의 @CreatedDate, @LastModifiedDate 어노테이션이 제대로 작동하도록 함
*/
@SpringBootApplication
@EnableJpaAuditing
public class MiniSnsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniSnsApplication.class, args);
	}

}
