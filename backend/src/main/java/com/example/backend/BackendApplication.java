package com.example.backend;

import com.example.backend.payload.request.AuthRegistrationRequest;
import com.example.backend.security.auth.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareProvider")
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthService authService
	) {
		return args -> {
			var admin = AuthRegistrationRequest.builder()
					.firstName("adminFirst")
					.lastName("adminLast")
					.email("admin@mail.com")
					.username("admin")
					.password("password")
					.build();
			log.info("Admin token: " + authService.register(admin).getAccessToken());

			var manager = AuthRegistrationRequest.builder()
					.firstName("managerFirst")
					.lastName("managerLast")
					.email("manager@mail.com")
					.username("manager")
					.password("password")
					.build();
			log.info("Manager token: " + authService.register(manager).getAccessToken());
		};
	}
}