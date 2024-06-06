package com.todolist.aplication.todo.dto.request

import com.todolist.domain.todo.enums.Status
import com.todolist.utils.ValidationUtil

data class TodoUpdateRequest(
    val id: String,
    val content: String,
    val status: Status
) {
    init {
        require(id.isNotBlank()) { "Id must not be blank" }
        require(content.isNotBlank()) { "Content must not be blank" }
        require(ValidationUtil.isValidTodoStatus(status)) { "Status must be one of [TODO, IN_PROGRESS, DONE, PENDING]"}
    }
}