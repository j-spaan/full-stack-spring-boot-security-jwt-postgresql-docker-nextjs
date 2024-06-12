package com.example.backend.security.token;

import com.example.backend.security.user.User;

/**
 * @author Jeffrey Spaan

 * @since 2024-06-12
 * @see com.example.backend.security.token.TokenServiceImpl
 */
public interface TokenService {
    public abstract Boolean isTokenValid(String token);
    public abstract void saveTokenByUser(String token, User user);
    public abstract void revokeAllTokensByUserId(Long id);
    public abstract void revokeTokenByJwt(String token);
}
