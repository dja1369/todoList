package com.learn.todolist.infra;

import com.learn.todolist.config.AllowedConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null) {
            token = token.replace("Bearer", "").trim();
        } else {
            token = "";
        }
        if (Arrays.asList(AllowedConfig.ALLOWED_URLS).contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!token.isBlank()) {
            if (!tokenProvider.validateToken(token)) {
                throw new IllegalArgumentException("Invalid Token");
            }
            SecurityContextHolder.getContext().setAuthentication(tokenProvider.authenticateToken(token));
            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
