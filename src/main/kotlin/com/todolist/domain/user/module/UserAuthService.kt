package com.todolist.domain.user.module

import com.todolist.domain.user.entity.User

interface UserAuthService {
    /**
     * 유저 이메일 검증
     */
    fun validateEmail(email: String): Boolean

    /**
     * 유저 닉네임 검증
     */
    fun validateNickName(nickName: String): Boolean

    /**
     * 유저 로그인 서비스
     */
    fun validateLogin(email: String, password: String): User
}