package com.todolist.utils

import com.todolist.domain.todo.enums.Status

object ValidationUtil {
    fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex(
            "^[A-Za-z0-9+_.-]+@(.+)$"
        )
        return emailRegex.matches(email)
    }
    fun isValidTodoStatus(status: Status): Boolean {
        return when (status) {
            Status.TODO, Status.IN_PROGRESS, Status.DONE, Status.PENDING -> true
        }
    }
}