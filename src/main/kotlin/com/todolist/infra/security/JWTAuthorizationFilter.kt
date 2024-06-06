package com.todolist.infra.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class JWTAuthorizationFilter(
    private val tokenProvider: TokenProvider
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        var token = ""
        request.getHeader("Authorization")?.let {
            token = it.replace("bearer", "", ignoreCase = true).trimIndent()
        }
        when(request.requestURI){
            "/api/v1/auth/login", "/api/v1/registers/register", "/swagger-ui/**", "/v3/api-docs" -> {
                filterChain.doFilter(request, response)
                return
            }
            else -> {
                if (token.isNotBlank()){
                    check(tokenProvider.validateToken(token)) {"Invalid Token"}
                    SecurityContextHolder.getContext().authentication = tokenProvider.authenticateToken(token)
                }
            }
        }
        filterChain.doFilter(request, response)
        return
    }
}