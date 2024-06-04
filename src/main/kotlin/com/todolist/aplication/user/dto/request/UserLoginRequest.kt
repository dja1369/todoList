package com.todolist.aplication.user.dto.request

import java.util.regex.Pattern

data class UserLoginRequest(
    val email: String,
    val password: String
)    {
    init {
        require(isValidEmail(email)) { "Invalid email format: $email" }
        require(password.isNotBlank()) { "Password must be a non-blank string" }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(.+)$"
        )
        val matcher = emailRegex.matcher(email)
        return matcher.find()
    }
}

