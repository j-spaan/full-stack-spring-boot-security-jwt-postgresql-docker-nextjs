package com.example.backend.config;

import com.example.backend.audit.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.UUID;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAwareProvider")
public class PersistenceConfig {

    @Bean
    public AuditorAware<UUID> auditorAwareProvider() {
        return new AuditorAwareImpl();
    }
}
