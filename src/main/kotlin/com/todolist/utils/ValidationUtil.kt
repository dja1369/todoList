package com.todolist.utils

object ValidationUtil {
    fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex(
            "^[A-Za-z0-9+_.-]+@(.+)$"
        )
        return emailRegex.matches(email)
    }
}