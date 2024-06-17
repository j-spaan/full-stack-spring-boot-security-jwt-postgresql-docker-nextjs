package com.example.backend.config.handler;

import com.example.backend.http.HttpRequestService;
import com.example.backend.security.jwt.JwtService;
import com.example.backend.security.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

/**
 * This custom logout handler invalidates the session and revokes the token.
 *
 * <p>Dependencies:</p>
 * <ul>
 *   <li>{@link HttpRequestService}: This class provides operations related to handling HTTP requests, such as retrieving the authorization header from the request.</li>
 *   <li>{@link JwtService}: This class is responsible for operations related to JSON Web Tokens (JWTs). This includes generating, validating, and parsing JWTs.</li>
 *   <li>{@link TokenService}: This class provides operations related to handling tokens, such as validating tokens, saving tokens for a user, and revoking tokens.</li>
 * </ul>
 *
 * @author Jeffrey Spaan
 * @since 2024-06-13
 * @see org.springframework.security.web.authentication.logout.LogoutHandler
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

  private final HttpRequestService httpRequestService;

  private final JwtService jwtService;

  private final TokenService tokenService;

  @Override
  public void logout(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication
  ) {
    request.getSession().invalidate();
    SecurityContextHolder.clearContext();

    final String jwt = httpRequestService.extractBearerToken();
    final String userEmail = jwtService.extractSubject(jwt);

    if (userEmail != null && jwtService.isTokenValid(jwt, userEmail) && Boolean.TRUE.equals(tokenService.isTokenValid(jwt))) {
      tokenService.revokeTokenByJwt(jwt);
    }
  }
}