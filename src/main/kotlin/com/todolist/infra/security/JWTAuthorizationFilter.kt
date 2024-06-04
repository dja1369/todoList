package com.todolist.infra.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
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
        var token: String?
        val authorizationHeader = request.getHeader("Authorization") ?: null
        authorizationHeader?.let{
            token = it.replace("Bearer", "").trimIndent()
        }
        when(request.requestURI){
            "/api/v1/login", "/api/v1/signup", "/swagger-ui/**", "/v3/api-docs" -> {
                filterChain.doFilter(request, response)
                return
            }
        }
        filterChain.doFilter(request, response)
        return
    }
}