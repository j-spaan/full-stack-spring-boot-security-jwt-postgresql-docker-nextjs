package com.example.backend.config;

import com.example.backend.audit.AuditorAwareImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.UUID;

@Configuration
public class AppConfig {

    @Bean
    public AuditorAware<UUID> auditorAwareProvider() {
        return new AuditorAwareImpl();
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
