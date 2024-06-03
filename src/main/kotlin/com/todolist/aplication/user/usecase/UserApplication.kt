package com.todolist.aplication.user.usecase

import com.todolist.aplication.user.dto.request.UserLoginRequest
import com.todolist.domain.user.module.UserAuthService
import com.todolist.infra.security.TokenProvider
import org.springframework.stereotype.Service

@Service
class UserApplication(
    private val userAuthServiceImpl: UserAuthService,
    private val tokenProvider: TokenProvider
) {
    fun isLogin(request: UserLoginRequest): String {
        val user = userAuthServiceImpl.validateLogin(request.email, request.password)
        val token = tokenProvider.createToken(user)
        return token
    }
}