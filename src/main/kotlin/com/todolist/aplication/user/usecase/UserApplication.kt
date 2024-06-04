package com.todolist.aplication.user.usecase

import com.todolist.aplication.user.dto.request.UserLoginRequest
import com.todolist.aplication.user.dto.request.UserRegisterRequest
import com.todolist.domain.common.CommonResponse
import com.todolist.domain.user.module.UserAuthService
import com.todolist.domain.user.module.UserRegisterService
import com.todolist.infra.security.TokenProvider
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class UserApplication(
    private val userAuthServiceImpl: UserAuthService,
    private val userRegisterServiceImpl: UserRegisterService,
    private val tokenProvider: TokenProvider
) {
    fun isLogin(request: UserLoginRequest): CommonResponse {
        val user = userAuthServiceImpl.validateLogin(request.email, request.password)
        val token = tokenProvider.createToken(user)
        return CommonResponse(
            status = HttpStatus.OK.value(),
            message = "로그인 성공",
            result = token
        )
    }

    @Transactional
    fun isRegister(request: UserRegisterRequest): CommonResponse {
        userAuthServiceImpl.validateEmail(request.email)
        userAuthServiceImpl.validateNickName(request.nickName)
        val isRegistered = userRegisterServiceImpl.registerUser(request.email, request.nickName, request.password)
        return CommonResponse(
            status = HttpStatus.OK.value(),
            message = "회원가입 성공",
            result = isRegistered
        )
    }
}