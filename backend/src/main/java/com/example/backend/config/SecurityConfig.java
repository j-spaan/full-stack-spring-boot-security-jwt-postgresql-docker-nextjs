package com.example.backend.config;

import com.example.backend.config.handler.CustomAccessDeniedHandler;
import com.example.backend.config.handler.CustomLogoutHandler;
import com.example.backend.config.handler.CustomLogoutSuccessHandler;
import com.example.backend.config.handler.CustomAuthenticationEntryPointHandler;
import com.example.backend.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    private final CustomLogoutHandler customLogoutHandler;

    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final CustomAccessDeniedHandler accessDeniedHandler;

    private final CustomAuthenticationEntryPointHandler authenticationEntryPointHandler;

    private static final String ADMIN = "ADMIN";
    private static final String MANAGER = "MANAGER";

    private static final String[] WHITE_LIST_URL = {
            "/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPointHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(WHITE_LIST_URL)
                        .permitAll()
                        .requestMatchers(AppConstants.Request.MANAGEMENT).hasAnyRole(ADMIN, MANAGER)
                        .anyRequest()
                        .authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/auth/logout")
                                .addLogoutHandler(customLogoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> {
                                    customLogoutSuccessHandler.onLogoutSuccess(request, response, authentication);
                                    SecurityContextHolder.clearContext();
                                })
                );
        return http.build();
    }
}