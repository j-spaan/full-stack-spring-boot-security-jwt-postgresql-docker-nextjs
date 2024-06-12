package com.example.backend.security.token;

import com.example.backend.security.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * This is a service class for managing tokens in the application.
 *
 * <p>Dependencies:</p>
 * <ul>
 *   <li>{@link TokenRepository}: Spring's  interface that defines methods for interacting with the data source that stores Token objects.</li>
 * </ul>
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@code @Service}: This annotation is a specialized form of @Component, allows for implementation classes to be autodetected through classpath scanning.</li>
 *   <li>{@code @RequiredArgsConstructor}: This is a Lombok annotation to automatically generate a constructor with required arguments. Required arguments are final fields and fields with constraints such as @NonNull.</li>
 * </ul>
 *
 * @author Jeffrey Spaan
 * @since 2024-06-12
 */
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    /**
     * Checks if a token is valid.
     * A token is considered valid if it is not expired and not revoked.
     *
     * @param token the token to check
     * @return true if the token is valid, false otherwise
     */
    @Override
    public Boolean isTokenValid(String token) {
        return tokenRepository.findByToken(token)
                .map(t -> !t.isExpired() && !t.isRevoked())
                .orElse(false);
    }

    /**
     * Saves a new token for a user.
     * The new token is not expired and not revoked by default.
     *
     * @param token the token to save
     * @param user the user for whom the token is saved
     */
    @Override
    public void saveTokenByUser(String token, User user) {
        var newToken = Token.builder()
                .user(user)
                .token(token)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(newToken);
    }

    /**
     * Revokes all valid tokens for a user by user id.
     * A token is considered revoked if it is marked as expired and revoked.
     *
     * @param id the id of the user for whom to revoke all tokens
     */
    @Override
    public void revokeAllTokensByUserId(UUID id) {
        tokenRepository.findAllValidTokenByUser(id)
                .forEach(token -> {
                    token.setExpired(true);
                    token.setRevoked(true);
                    tokenRepository.save(token);
                });
    }

    /**
     * Revokes a token by its JWT.
     * A token is considered revoked if it is marked as expired and revoked.
     *
     * @param token the JWT of the token to revoke
     */
    @Override
    public void revokeTokenByJwt(String token) {
        tokenRepository.findByToken(token)
                .ifPresent(currentToken -> {
                    currentToken.setExpired(true);
                    currentToken.setRevoked(true);
                    tokenRepository.save(currentToken);
                });
    }
}