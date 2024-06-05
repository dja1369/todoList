package com.todolist.aplication.user.dto.request

import com.todolist.utils.ValidationUtil.isValidEmail

data class UserLoginRequest(
    val email: String,
    val password: String
)    {
    init {
        require(isValidEmail(email)) { "Invalid email format" }
        require(password.isNotBlank()) { "Password must be a non-blank string" }
    }

}

