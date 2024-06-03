package com.todolist.domain.user.module

interface UserRegisterService {
    /**
     * 유저 등록
     */
    fun registerUser(email: String, nickName: String, password: String): Boolean

    /**
     * 유저 탈퇴 서비스
     */
    fun withdrawalUser(email: String, password: String): Boolean
}
