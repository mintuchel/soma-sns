package com.soma.mini_sns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MiniSnsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniSnsApplication.class, args);
	}

}
