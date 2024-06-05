package com.todolist.aplication.todo.dto.request

data class TodoCreateRequest(
    val title: String,
    val content: String?
) {
    init {
        require(title.isNotBlank()) { "Title must not be blank" }
    }
}