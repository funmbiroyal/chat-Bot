package dev.gracie.elearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ELearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(ELearnApplication.class, args);
	}

}
