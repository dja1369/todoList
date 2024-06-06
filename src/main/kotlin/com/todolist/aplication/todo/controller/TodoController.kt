package com.todolist.aplication.todo.controller

import com.todolist.aplication.todo.dto.request.TodoCreateRequest
import com.todolist.aplication.todo.dto.request.TodoUpdateRequest
import com.todolist.aplication.todo.usecase.TodoApplication
import com.todolist.domain.common.CommonResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class TodoController(
    private val todoApplication: TodoApplication
)   {
    @PostMapping("/todos/todo")
    fun createTodo(@RequestBody request: TodoCreateRequest): ResponseEntity<CommonResponse> {
        val response = todoApplication.processCreateTodo(request)
        return ResponseEntity(response, HttpStatus.OK)
    }

    @PatchMapping("/todos/{id}")
    fun updateTodo(@RequestBody request: TodoUpdateRequest): ResponseEntity<CommonResponse> {
        val response = todoApplication.processUpdateTodo(request)
        return ResponseEntity(response, HttpStatus.OK)
    }

    @DeleteMapping("/todos/{id}")
    fun deleteTodo(@PathVariable id: String): ResponseEntity<CommonResponse> {
        val response = todoApplication.processDeleteTodo(id)
        return ResponseEntity(response, HttpStatus.OK)
    }

    @GetMapping("/todos")
    fun getAllTodo(): ResponseEntity<CommonResponse> {
        val response = todoApplication.processGetAllTodo()
        return ResponseEntity(response, HttpStatus.OK)
    }

    @GetMapping("/todos/{id}")
    fun getTodoDetail(@PathVariable id: String): ResponseEntity<CommonResponse> {
        val response = todoApplication.processGetTodoDetail(id)
        return ResponseEntity(response, HttpStatus.OK)
    }

    @GetMapping("/todos/recent-todo")
    fun getRecentTodo(): ResponseEntity<CommonResponse> {
        val response = todoApplication.processGetRecentTodo()
        return ResponseEntity(response, HttpStatus.OK)
    }


}