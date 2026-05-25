package com.soma.mini_sns;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.soma.mini_sns.domain.*.mapper")
public class MiniSnsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniSnsApplication.class, args);
	}

}
