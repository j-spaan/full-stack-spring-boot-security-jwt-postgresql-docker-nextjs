package com.example.backend.config.bean;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperBeanConfig {

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
