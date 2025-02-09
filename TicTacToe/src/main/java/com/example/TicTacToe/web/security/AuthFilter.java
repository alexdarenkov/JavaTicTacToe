package com.example.TicTacToe.web.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class AuthFilter extends GenericFilterBean {
    private static final String AUTH_PREFIX = "Bearer ";

    private final JwtProvider jwtProvider;
    private final List<String> excludedUrls = List.of("/auth/register", "/auth/login");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        final String accessToken = getTokenFromRequest((HttpServletRequest) request);

        if (accessToken != null && jwtProvider.validateAccessToken(accessToken)) {
            Claims claims = jwtProvider.getAccessClaims(accessToken);
            Authentication auth = JwtUtil.createAuthentication(claims);
            auth.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(auth);
            System.out.println("Authorities: " + auth.getAuthorities());
        }

        chain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);

        if (StringUtils.hasText(bearer) && bearer.startsWith(AUTH_PREFIX)) {
            return bearer.substring(AUTH_PREFIX.length());
        }
        return null;
    }
}