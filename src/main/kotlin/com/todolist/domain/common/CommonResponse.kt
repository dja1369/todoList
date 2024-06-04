package com.todolist.domain.common

data class CommonResponse (
    val status: Int,
    val message: String,
    val result: Any
)