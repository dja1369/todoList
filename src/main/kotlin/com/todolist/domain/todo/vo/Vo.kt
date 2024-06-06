package com.todolist.domain.todo.vo

import com.todolist.domain.todo.enums.Status

data class TodoVo(
    val id: String,
    val title: String,
    val status: Status
)

data class TodoVoDetail(
    val id: String,
    val title: String,
    val content: String?,
    val status: Status
)

data class TodoVoList (
    val todoList: List<TodoVo>
)