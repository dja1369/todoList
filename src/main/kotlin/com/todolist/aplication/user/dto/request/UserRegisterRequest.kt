package com.todolist.aplication.user.dto.request

data class UserRegisterRequest (
    val nickName: String,
    val email: String,
    val password: String
)