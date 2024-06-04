package com.todolist.aplication.user.usecase

import com.todolist.aplication.user.dto.request.UserLoginRequest
import com.todolist.aplication.user.dto.request.UserRegisterRequest
import com.todolist.domain.common.CommonResponse
import com.todolist.domain.user.module.UserAuthService
import com.todolist.domain.user.module.UserRegisterService
import com.todolist.infra.security.TokenProvider
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class UserApplication(
    private val userAuthServiceImpl: UserAuthService,
    private val userRegisterServiceImpl: UserRegisterService,
    private val tokenProvider: TokenProvider
) {
    fun isLogin(request: UserLoginRequest): CommonResponse {
        val user = userAuthServiceImpl.validateLogin(request.email, request.password)
        logger.info { "Validate Login Done"}
        val token = tokenProvider.createToken(user)
        logger.info { "Token Generate Done Will Be Return"}
        return CommonResponse(
            message = "로그인 성공",
            result = token
        )
    }

    @Transactional
    fun isRegister(request: UserRegisterRequest): CommonResponse {
        userAuthServiceImpl.validateEmail(request.email)
        logger.info { "Validate Email Done" }
        userAuthServiceImpl.validateNickName(request.nickName)
        logger.info { "Validate NickName Done" }
        val isRegistered = userRegisterServiceImpl.registerUser(request.email, request.nickName, request.password)
        logger.info { "Register Done" }
        return CommonResponse(
            message = "회원가입 성공",
            result = isRegistered
        )
    }

    @Transactional
    fun isWithdrawal(request: UserLoginRequest): CommonResponse {
        userAuthServiceImpl.validateLogin(request.email, request.password)
        logger.info { "Validate Login Done" }
        val isWithdrawal = userRegisterServiceImpl.withdrawalUser(request.email, request.password)
        logger.info { "Withdrawal Done" }
        return CommonResponse(
            message = "탈퇴 성공",
            result = isWithdrawal
        )
    }
}