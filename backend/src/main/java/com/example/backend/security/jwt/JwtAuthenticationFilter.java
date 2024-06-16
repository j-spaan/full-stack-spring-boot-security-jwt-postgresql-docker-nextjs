package com.example.backend.security.jwt;

import com.example.backend.http.HttpConstants;
import com.example.backend.security.token.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter that checks if the JWT token is valid and if the user is authenticated.
 *
 * <p>Dependencies:</p>
 * <ul>
 *   <li>{@link JwtService}: This class is responsible for operations related to JSON Web Tokens (JWTs). This includes generating, validating, and parsing JWTs.</li>
 *   <li>{@link UserDetailsService}: This interface is used to retrieve user-related data. It is used by the {@link AuthenticationManager} to authenticate a user.</li>
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

  private final JwtService jwtService;

  private final UserDetailsService userDetailsService;

  private final TokenService tokenService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authHeader == null || !authHeader.startsWith(HttpConstants.Header.BEARER)) {
      filterChain.doFilter(request, response);
      return;
    }
    final String jwt = authHeader.substring(HttpConstants.Header.BEARER.length()).trim();
    final String userEmail = jwtService.extractSubject(jwt);
    if (userEmail != null
            && SecurityContextHolder.getContext().getAuthentication() == null
            && jwtService.isTokenValid(jwt, userEmail)
            && Boolean.TRUE.equals(tokenService.isTokenValid(jwt))) {
      setAuthentication(userEmail, request);
    }
    filterChain.doFilter(request, response);
  }

  private void setAuthentication(String userEmail, HttpServletRequest request) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
    );
    authToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
    );
    SecurityContextHolder.getContext().setAuthentication(authToken);
  }
}