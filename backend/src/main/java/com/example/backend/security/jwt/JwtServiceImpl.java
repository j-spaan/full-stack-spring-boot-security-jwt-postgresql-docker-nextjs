package com.example.backend.security.jwt;

import com.example.backend.http.HttpRequestService;
import com.example.backend.i18n.I18nService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;

/**
 * Implementation of the {@link JwtService} interface providing JWT operations.
 * This service handles the creation, validation, and extraction of claims from JWT tokens.
 *
 * <p>Configured with the following properties:</p>
 * <ul>
 *   <li><code>application.security.jwt.issuer</code>: The issuer of the JWT.</li>
 *   <li><code>application.security.jwt.secret-key</code>: The secret key used for signing JWTs.</li>
 *   <li><code>application.security.jwt.expiration</code>: The expiration time of the JWT in milliseconds.</li>
 *   <li><code>application.security.jwt.refresh-token.expiration</code>: The expiration time of the refresh token in milliseconds.</li>
 * </ul>
 *
 * <p>Dependencies:</p>
 * <ul>
 *   <li>{@link I18nService}: Service for internationalized messages.</li>
 *   <li>{@link HttpRequestService}: Service for HTTP request-related information.</li>
 * </ul>
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@code @Slf4j}: Enables logging using SLF4J.</li>
 *   <li>{@code @Service}: Marks this class as a Spring service.</li>
 *   <li>{@code @RequiredArgsConstructor}: Generates a constructor with required arguments.</li>
 * </ul>
 *
 * @author Jeffrey Spaan
 * @since 2024-06-10
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

  @Value("${application.security.jwt.issuer}")
  private String issuer;
  @Value("${application.security.jwt.secret-key}")
  private String secretKey;
  @Value("${application.security.jwt.expiration}")
  private long jwtExpiration;
  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshExpiration;

  private final I18nService i18nService;
  private final HttpRequestService httpRequestService;

  /**
   * Extracts the subject (typically the user email) from the given JWT token.
   *
   * @param jwt the JWT token
   * @return the subject extracted from the JWT token
   */
  @Override
  public String extractSubject(String jwt) {
    return extractClaim(jwt, Claims::getSubject);
  }

  /**
   * Extracts the issuer from the given JWT token.
   *
   * @param jwt the JWT token
   * @return the issuer extracted from the JWT token
   */
  private String extractIssuer(String jwt) {
    return extractClaim(jwt, Claims::getIssuer);
  }

  /**
   * Extracts a specific claim from the given JWT token using the
   * provided claims resolver function.
   *
   * @param <T> the type of the claim
   * @param jwt the JWT token
   * @param claimsResolver the function to extract the claim
   * @return the claim extracted from the JWT token
   */
  private <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
    try {
      return claimsResolver.apply(extractAllClaims(jwt));
    } catch (SignatureException e) {
      this.logErrorMessage("log.jwt.si.invalid.jwt", e.getMessage());
    } catch (MalformedJwtException e) {
      this.logErrorMessage("log.jwt.si.malformed.jwt", e.getMessage());
    } catch (ExpiredJwtException e) {
      this.logErrorMessage("log.jwt.si.expired.jwt", e.getMessage());
    } catch (UnsupportedJwtException e) {
      this.logErrorMessage("log.jwt.si.unsupported.jwt", e.getMessage());
    }
    // TODO register IP when unsupported JWT is provided
    throw new SignatureException("Invalid JWT token");
  }

  /**
   * Generates a JWT token with the given email as the subject.
   *
   * @param email the email to be used as the subject
   * @return the generated JWT token
   */
  @Override
  public String generateToken(String email) {
    return generateToken(new HashMap<>(), email);
  }

  /**
   * Generates a JWT token with the given extra claims and email as the subject.
   *
   * @param extraClaims additional claims to be included in the token
   * @param email the email to be used as the subject
   * @return the generated JWT token
   */
  private String generateToken(
      Map<String, Object> extraClaims,
      String email
  ) {
    return buildToken(extraClaims, email, jwtExpiration);
  }

  /**
   * Generates a refresh token with the given email as the subject.
   *
   * @param email the email to be used as the subject
   * @return the generated refresh token
   */
  @Override
  public String generateRefreshToken(
      String email
  ) {
    return buildToken(new HashMap<>(), email, refreshExpiration);
  }

  /**
   * Builds a JWT token with the given extra claims, email, and expiration time.
   *
   * @param extraClaims additional claims to be included in the token
   * @param email the email to be used as the subject
   * @param expiration the expiration time in milliseconds
   * @return the generated JWT token
   */
  private String buildToken(
          Map<String, Object> extraClaims,
          String email,
          long expiration
  ) {
    return Jwts
            .builder()
            .claims(extraClaims)
            .issuer(issuer)
            .subject(email)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey())
            .compact();
  }

  /**
   * Validates the given JWT token against the provided email.
   *
   * @param jwt the JWT token
   * @param email the email to validate against
   * @return true if the token is valid, false otherwise
   */
  @Override
  public boolean isTokenValid(String jwt, String email) {
    final String jwtSubject = extractSubject(jwt);
    final String jwtIssuer = extractIssuer(jwt);

    if (StringUtils.hasText(jwtSubject) && StringUtils.hasText(jwtIssuer)) {
      return (jwtSubject.equals(email)) && jwtIssuer.equals(issuer) && !isTokenExpired(jwt);
    }
    return false;
  }

  /**
   * Checks if the given JWT token is expired.
   *
   * @param token the JWT token
   * @return true if the token is expired, false otherwise
   */
  private boolean isTokenExpired(String token) {
    Date expirationDate = extractExpiration(token);

    if (expirationDate != null) {
      return expirationDate.before(new Date());
    }
    return false;
  }

  /**
   * Extracts the expiration date from the given JWT token.
   *
   * @param token the JWT token
   * @return the expiration date extracted from the JWT token
   */
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Extracts all claims from the given JWT token.
   *
   * @param token the JWT token
   * @return the claims extracted from the JWT token
   */
  private Claims extractAllClaims(String token) {
    return Jwts
            .parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
  }

  /**
   * Gets the signing key for the JWT.
   *
   * @return the signing key
   */
  private SecretKey getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  /**
   * Logs an error message using the internationalization service and HTTP request service.
   *
   * @param code the code for the log message
   * @param error the error message to log
   */
  private void logErrorMessage(String code, String error) {
    log.error(i18nService.getLogMessage(code),
            httpRequestService.extractIp(),
            httpRequestService.extractUsername(),
            error
    );
  }
}