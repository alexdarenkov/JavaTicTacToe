package com.example.TicTacToe.web.security;

import com.example.TicTacToe.domain.model.JwtAuthentication;
import com.example.TicTacToe.domain.model.Role;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    public static JwtAuthentication createAuthentication(Claims claims) {
        final JwtAuthentication jwtAuthentication = new JwtAuthentication();
        jwtAuthentication.setId(UUID.fromString(claims.getSubject()));
        jwtAuthentication.setRoles(parseRoles(claims));
        return jwtAuthentication;
    }

    private static Set<Role> parseRoles(Claims claims) {
        final List<String> roles = claims.get("roles", List.class);
        return roles.stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }
}
