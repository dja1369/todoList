package com.todolist.aplication.user.dto.request

import com.todolist.utils.ValidationUtil.isValidEmail

data class UserRegisterRequest (
    val nickName: String,
    val email: String,
    val password: String
){
    init {
        require(nickName.isNotBlank()) { "NickName must be a non-blank string" }
        require(isValidEmail(email)) { "Invalid email format" }
        require(password.isNotBlank()) { "Password must be a non-blank string" }
    }
}