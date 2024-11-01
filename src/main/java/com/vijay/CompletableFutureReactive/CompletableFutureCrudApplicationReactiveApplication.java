package com.vijay.CompletableFutureReactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
//@EnableAsync     if we use custom pull of thread the this is optional
@SpringBootApplication
public class CompletableFutureCrudApplicationReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompletableFutureCrudApplicationReactiveApplication.class, args);
	}

}
