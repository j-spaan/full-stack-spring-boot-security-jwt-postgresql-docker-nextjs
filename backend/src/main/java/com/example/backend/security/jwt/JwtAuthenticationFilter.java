package com.example.backend.security.jwt;

import com.example.backend.http.HttpRequestService;
import com.example.backend.security.auth.AuthService;
import com.example.backend.security.token.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter that checks if the JWT token is valid and if the user is authenticated.
 *
 * <p>Dependencies:</p>
 * <ul>
 *   <li>{@link AuthService}: This service provides functionality to validate authentication objects.</li>
 *   <li>{@link HttpRequestService}: Represents an HTTP request and provides methods to access request parameters, headers, and attributes in a web application.</li>
 *   <li>{@link JwtService}: This class is responsible for operations related to JSON Web Tokens (JWTs). This includes generating, validating, and parsing JWTs.</li>
 *   <li>{@link TokenService}: This class provides operations related to handling tokens, such as validating tokens, saving tokens for a user, and revoking tokens.</li>
 * </ul>
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@code @Component}: This annotation marks this class as a Spring component that will be autodetected during classpath scanning.</li>
 *   <li>{@code @RequiredArgsConstructor}: This is a Lombok annotation to automatically generate a constructor with required arguments. Required arguments are final fields and fields with constraints such as @NonNull.</li>
 * </ul>
 *
 * @author Jeffrey Spaan
 * @since 2024-06-14
 * @see OncePerRequestFilter
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final AuthService authService;
  private final HttpRequestService httpRequestService;
  private final JwtService jwtService;
  private final TokenService tokenService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    final String jwt = httpRequestService.getBearerToken();
    final String userEmail = jwtService.extractSubject(jwt);

    if (userEmail != null && isTokenValid(jwt, userEmail)) {
      authService.setAuthentication(userEmail, request);
    }
    filterChain.doFilter(request, response);
  }

  private boolean isTokenValid(String jwt, String userEmail) {
    return jwtService.isTokenValid(jwt, userEmail) && Boolean.TRUE.equals(tokenService.isTokenValid(jwt));
  }
}